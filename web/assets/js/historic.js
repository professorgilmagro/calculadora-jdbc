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
} ) ;