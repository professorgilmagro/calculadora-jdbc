$(function(){
    var calculator = {
            CURSOR_NUMERATOR: 1,
            CURSOR_DENOMINATOR: 2,
            FRAC_SEPARATOR: '/',
            OPERATORS : ["+", "-", "÷", "×"],
            cursor: 1,
            new_frac : true,

            defaults : {
                numerador : "#calc-numerador" ,
                denominador : "#calc-denominador" ,
                divisor_symbol: ".icon-divide",
                buttons: {
                    numbers: ".calc-board button" ,
                    backspace: "#btn-backspace" ,
                    operator: ".btn-operator" ,
                    decimal: "#btn-decimal" ,
                    clean: "#btn-clean" ,
                    execute: "#btn-calculate" ,
                    cursor_toggle: "#btn-toogle-cursor"
                },
                screen: "#calc-screen",
                mathText: "#mathText" ,
                warnings: "tr.visor td.content ul.warnings",
                category_info: "tr.visor td.content div.tipo-fracao"
            } ,
            init : function( options ) {
                this._initialize( options ) ;
                this.clean();
                this.numbers();
                this.backspace();
                this.operators();
                this.key_events();
                this.toggleCursor.init( this );
                
                
            } ,
            _initialize: function( options ) {
                this.settings = $.extend( {}, this.defaults, options ) ;
                this.$screen = $(this.settings.screen);
                this.$mathText = $(this.settings.mathText);
                this.$warnings = $(this.settings.warnings);
                this.$category_info = $(this.settings.category_info);
                this.$btnCursor = $(this.settings.buttons.cursor_toggle);
                this.$cursorView = this.$screen.parents(".content").find(".cursor-frac");
            } ,
            _updateMathContent: function() {
                var display = null;
                
                if ( this.getTeX().length === 1 ) {
                    $(".visor.classification").hide();
                    $(".visor .details").hide();
                    this._clean_info();
                }
                
                MathJax.Hub.Queue(function () {
                  display = MathJax.Hub.getAllJax("calc-screen")[0];
                });

                if ( display !== null ) {
                    MathJax.Hub.Queue(["Text", display, this.getTeX()]);
                }
            },
            getTeX: function() {
                return this.$mathText.val().toString() ;
            } ,
            numbers: function() {
                var settings = this.settings ;
                var calc = this ;
                $(settings.buttons.numbers).click(function(){
                    var typed_value = $(this).text();

                    if( ! $.isNumeric(typed_value)){
                        return ;
                    }

                    // remonta a nova mathText
                    var newTeX = calc._reassembleTeX(typed_value);
                    calc.$mathText.val(newTeX);
                    calc._updateMathContent();
                });
            } ,
            _reassembleTeX: function(typed_value){
                var fracs = this.getTeX().split(/\+|-|×|÷/);
                var fracEdit = fracs.pop().split(this.FRAC_SEPARATOR);
                
                if ( fracEdit.length === 1 
                        && $.isNumeric(fracEdit[0]) 
                        && parseInt(fracEdit[0]) > 0 
                        && this.cursor === this.CURSOR_DENOMINATOR 
                    ) {
                    
                    fracEdit[1] = "";
                }

                // seleciona a parte a ser editada (numerador ou denominador)
                var elect = ( fracEdit.length === 2 && this.cursor === this.CURSOR_DENOMINATOR ) ? 1 : 0;
                fracEdit[elect] += typed_value.toString();

                var used = [];
                for( var i = 0; i < this.getTeX().length; i++ ) {
                    var s = this.getTeX().substr(i, 1) ;
                    if ( $.inArray(s, this.OPERATORS) !== -1 ){
                        used.push(s);
                    }
                }

                var newTeX = "";
                if ( used.length > 0 ) {
                   for( i = 0; i < used.length; i++ ) {
                      newTeX += fracs[i] + used[i];
                   }
                }

                newTeX += fracEdit.join(this.FRAC_SEPARATOR).toString();
                return newTeX ;
            } ,
            backspace: function() {
               var settings = this.settings ;
               var calc = this ;

               $(settings.buttons.backspace).click(function(){
                   calc._clean_info();
                   if ( calc.getTeX().length === 0 ) {
                       return ;
                   }
                   
                   // remove o último dígito
                   var newTeX = calc.getTeX().substring(0, calc.getTeX().length - 1);
                   
                   // verifica se o próximo dígito é o separador, caso afirmativo, removê-lo-á 
                   if( newTeX.substr(-1,1) === calc.FRAC_SEPARATOR ){
                       newTeX = newTeX.substring(0, newTeX.length - 1);
                       calc.$btnCursor.trigger("click");
                   }
                   
                   // se o dígito que está sendo apagado for um operador e
                   // houver um denominador da fracao que está sendo apagada,
                   // o cursor deve ser movido do numerador para o denominador
                   if ( $.inArray(calc.getTeX().substr(-1,1), calc.OPERATORS) !== -1 ){
                       var fracs = newTeX.split(/\+|-|×|÷/);
                       calc.cursor = fracs.pop().split(calc.FRAC_SEPARATOR).length === 2 ? calc.CURSOR_NUMERATOR : calc.CURSOR_DENOMINATOR;
                       calc.$btnCursor.trigger("click");
                   }
                   
                   // se tudo foi removido, move o cursor para numerador
                    if($.isEmptyObject(newTeX) && calc.cursor === calc.CURSOR_DENOMINATOR){
                        calc.$btnCursor.trigger("click");
                    }
                   
                   calc.$mathText.val(newTeX);
                   calc._updateMathContent();
               });
            } ,
            operators: function() {
               var settings = this.settings ;
               var calc = this ;
               $(settings.buttons.operator).on( "click" , function(){
                    var s = calc.getTeX().substr(-1,1);
                    if ( s === calc.FRAC_SEPARATOR || $.inArray(s, calc.OPERATORS) !== -1 || $.isEmptyObject( calc.getTeX() ) ){
                        return ;
                    }
                   
                   // se o cursor estiver no denominador da fracao, retorna para o numerador
                    if(calc.cursor === calc.CURSOR_DENOMINATOR){
                        $(calc.settings.buttons.cursor_toggle).trigger("click");
                    }
                    
                    calc.$mathText.val(calc.getTeX() + $(this).text());
                    calc._updateMathContent();
                } ) ;
            } ,
            toggleCursor: {
                init : function(calc){
                    this.calc = calc;
                    var that = this;
                    
                    // cria o evento para o toggle de cursor ao clicar no botão 
                    // de alteração disposto no teclado da calculadora
                    calc.$btnCursor.on( "click" , function (e) {
                        e.preventDefault() ;

                        if ($.isEmptyObject(calc.getTeX())) return ;
                        if( calc.cursor === calc.CURSOR_NUMERATOR ) {
                           that.moveToDenominator();
                        }
                        else if( calc.cursor === calc.CURSOR_DENOMINATOR) {
                            that.moveToNumerator();
                        }
                    });
                    
                    // atribui o mesmo comportamento do botão ao sinalizador de
                    // cursor que é exibido no visor da calculadora
                    calc.$cursorView.on( "click" , function(){
                        $(calc.$btnCursor).trigger("click");
                    });
                },
                moveToNumerator: function(){
                    this._clean();
                    this.calc.$cursorView.find("span").first().addClass("active");
                    this.calc.$btnCursor.find("span").first().addClass("active");
                    this.calc.cursor = this.calc.CURSOR_NUMERATOR ;
                } ,
                moveToDenominator: function(){
                    this._clean();
                    this.calc.$cursorView.find("span").last().addClass("active");
                    this.calc.$btnCursor.find("span").last().addClass("active");
                    this.calc.cursor = this.calc.CURSOR_DENOMINATOR ;
                } ,
                _clean:function(){
                    this.calc.$cursorView.find("span").removeClass("active");
                    this.calc.$btnCursor.find("span").removeClass("active");
                }
            } ,
            clean: function() {
                var calc = this ;
                $(calc.settings.buttons.clean).on( "click" , function (e) {
                    e.preventDefault() ;
                    calc.$mathText.val("");
                    calc._updateMathContent();
                    calc._clean_info();
                    calc.toggleCursor.moveToNumerator();
                    $(".visor.classification").hide();
                    $(".visor .details").hide();
                }) ;
            } ,
            _clean_info: function(){
               this.$category_info.text("");
               this.$warnings.remove(); 
            } ,
            key_events: function() {
                var calc = this ;
                
                $(document).on("keyup" , function(e){
                    e.preventDefault() ;
                    console.log(e.keyCode)

                    switch (e.keyCode) {
                        case 13:
                            $(calc.settings.buttons.execute).trigger("click");
                            break;

                        case 8:
                        case 46:
                            $(calc.settings.buttons.backspace).trigger("click");
                            break;
                        
                        case 56:
                            $(calc.settings.buttons.operator).filter(":contains(×)").trigger("click");
                            break;

                        case 27:
                        case 67:
                            $(calc.settings.buttons.clean).trigger("click");
                            break;
                        
                         case 187:
                            $(calc.settings.buttons.operator).filter(":contains(+)").trigger("click");
                            break;

                        case 110:
                        case 190:
                        case 108:
                        case 188:
                            $(calc.settings.buttons.decimal).trigger("click");
                            break;
                            
                        case 189:
                            $(calc.settings.buttons.operator).filter(":contains(-)").trigger("click");
                            break;
                            
                        case 111:
                        case 191:
                            $(calc.settings.buttons.operator).filter(":contains(÷)").trigger("click");
                            break;
                            
                        case 220:
                            calc.$cursorView.trigger("click");
                            break;
                    }
                        
                });

                $(document).on( "keypress", function(e){
                    e.preventDefault() ;

                    var charCode = String.fromCharCode(e.which);
                    if ( $.isNumeric(charCode) && parseInt(charCode) < 10 ) {
                        $(calc.settings.buttons.numbers + ":contains(" + charCode + ")" ).trigger("click");
                    }
                });

                $(document).on( "keydown", function(e){
                   if( e.keyCode === 8 ) {
                       e.preventDefault();
                   }
                } );
            }
    };

    calculator.init();
});