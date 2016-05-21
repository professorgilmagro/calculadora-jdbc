$(function(){
    $('#tabela-historico').DataTable({
        "columnDefs": [ 
            {
                "targets": 5,
                "orderable": false,
            }
        ],
        "language": {
                "processing":   "a processar...",
                "lengthMenu":   "mostrar _MENU_ registos",
                "zeroRecords":  "Não foram encontrados resultados para a busca informada",
                "info": 	"Monstrando página _PAGE_ de _PAGES_",
                "infoEmpty":    "0 registo",
                "infoFiltered": "(filtrado de _MAX_ registos no total)",
                "infoPostFix":  "",
                "search":       "procurar:",
                "emptyTable":   "Não há dados de histórico registrado",
                "paginate": {
                    "first":    "primeiro",
                    "previous": "anterior",
                    "next":     "seguinte",
                    "last":     "último"
                }
        }
    });
    
    $('#tabela-historico td img.edit').on( "click" , function() {
        var $tr = $(this).parents("tr") ;
        $(this).hide();
        $tr.find(".preview").hide();
        $tr.find(".remove").hide();
        $tr.find(".cancel").show();
        $tr.find(".save").show();
        $tr.find(".hide").removeClass("hide");
    } ) ;
    
    $('#tabela-historico td img.cancel').on( "click" , function() {
        var $tr = $(this).parents("tr") ;
        $(this).hide();
        $tr.find(".preview").show();
        $tr.find(".edit").show();
        $tr.find(".remove").show();
        $tr.find(".save").hide();
        $tr.find("input").addClass("hide");
    });
    
    $('#tabela-historico td a.save').on( "click" , function(evt) {
        evt.preventDefault();

        var $tr = $(this).parents("tr") ;
        $.ajax({
            url : $(this).attr("href") ,
            method: "POST",
            data: $tr.find("input").serialize(),
            dataType: "JSON",
            success: function(data){
                if( data.hasOwnProperty("success") && data.success === true ) {
                    alert("Alterações realizada com sucesso.") ;
                    location.reload();
                    
                    return ;
                }
                
                alert("Houve um problema na alterações dos dados solicitados");
                $tr.find(".cancel").trigger("click");
            }
        }) ;
    });
} ) ;