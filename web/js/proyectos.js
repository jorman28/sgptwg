$(function() {
    $('#fechaInicio').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $("#responsable").typeahead({
        onSelect: function(item) {
            console.log(item);
        },
        ajax: {
            url: "ProyectosController",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 3,
            method: "POST",
            preDispatch: function(query) {
                return {search: query, accion: "hola"};
            }
        }
    });
});