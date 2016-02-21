/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var personasProyecto = {};

jQuery(function () {
    $('#fecha_estimada_inicio')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_estimada_terminacion').datetimepicker('setStartDate', $('#fecha_estimada_inicio').val());
            });
    $('#fecha_estimada_terminacion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_estimada_inicio').datetimepicker('setEndDate', $('#fecha_estimada_terminacion').val());
            });


    $('#fecha_real_inicio')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_real_terminacion').datetimepicker('setStartDate', $('#fecha_real_inicio').val());
            });
    $('#fecha_real_terminacion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_real_inicio').datetimepicker('setEndDate', $('#fecha_real_terminacion').val());
            });


    $("#participante")
            .typeahead({
                onSelect: function (item) {
                    if ($("#persona" + item.value)[0] === undefined) {
                        var persona = personasProyecto[item.value];
                        var html = persona.id;
                        $("#responsable").val(html);
                    }
                },
                ajax: {
                    url: "ProyectosController",
                    timeout: 500,
                    displayField: "nombre",
                    valueField: 'id',
                    triggerLength: 1,
                    items: 10,
                    method: "POST",
                    preDispatch: function (query) {
                        return {search: query, accion: "completarPersonas"};
                    },
                    preProcess: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var persona = data[i];
                            personasProyecto[persona.id] = persona;
                        }
                        return data;
                    }
                }
            });
});

//function pintarPersona(persona) {
////    var html = '    <li class="list-group-item" id="persona' + persona.id + '">'
////            + '         <div class="row">'
////            + '             <input type="hidden" id="idPersona' + persona.id + '" name="idPersonas" value="' + persona.id + '" />'
////            + '             <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">'
////            + '                 ' + persona.nombre
////            + '             </div>'
////            + '         </div>'
////            + '     </li>';
//var html = '<input type="hidden" id="responsable" name="responsable" value="'+ persona.id +'" />';
//    return html;
//}

function nuevaActividad() {
    $("#id").val('');
    $("#persona").val('0');
    $("#version").val('0');
    $("#descripcion").val('');
    $("#fecha_estimada_inicio").val('');
    $("#fecha_estimada_terminacion").val('');
    $("#fecha_real_inicio").val('');
    $("#fecha_real_terminacion").val('');
    $("#tiempo_estimado").val('');
    $("#tiempo_invertido").val('');
    $("#estado").val('0');
}

function consultarVersiones(idProyecto){
    $.ajax({
        type    :"POST",
        url     :"ActividadesController",
        dataType:"json",
        data    :{proyecto: idProyecto, accion: "consultarVersiones"},
        success: function(data) {
            if(data !== undefined){
                var html = "<option value='0'>SELECCIONE</option>";
                for(var version in data){
                    version = data[version];
                    html += "<option value='"+version.id+"'>"+version.nombre+"</option>";
                }
                $("#version").html(html);
            }
        },
        error: function(err){
            alert(err);
        }
    });
}
