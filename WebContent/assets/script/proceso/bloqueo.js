$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
 
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};
var controllerPage = function() {
	var ID;
	var populateForm = function()
	{
		$.ajax({
			url : "/HortiSafeMX/"+"json/productor/getAllOutFilter",
			type : "GET",
			data : "",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},
			success : function(data, textStatus, jqXHR) {
				var options = "";
				
				$(data).each(function(key, val){
					options += "<option value='"+val.codigo+"'>"+val.codigo+" | "+val.nombre+"</option>";
				})
				
				$('.codProductor').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		
		$.ajax({
			url : "/HortiSafeMX/"+"json/mercado/getAllOutFilter",
			type : "GET",
			data : "",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},

			success : function(data, textStatus, jqXHR) {
				var options = "";
				$(data).each(function(key, val){
					options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
				})
				$('.mercado').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		$.ajax({
			url : "/HortiSafeMX/"+"json/especie/getAllOutFilter",
			type : "GET",
			data : "",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},

			success : function(data, textStatus, jqXHR) {
				var options = "";
				
				$(data).each(function(key, val){
					options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";
				})
			
				$('.especie').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	}
	
	var getView = function() {

		var grid = new Datatable();

		grid.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {},
					onError : function(grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(grid) {
						// execute some code on ajax data load
					},
					loadingMessage : 'Loading...',
					dataTable : { 
						"responsive": false,
						"bStateSave" : true, 
						"lengthMenu" : [ [ 10, 20, 50, 100, 150, -1 ],[ 10, 20, 50, 100, 150, "All" ] ],
						"pageLength" : 20, // default record count per page
						"ajax" : {"url" : "/HortiSafeMX/"+"json/bloqueo/view"},
						"columnDefs" : [
						        { "visible": false, "targets": [0] },     
								{
									"targets" : [ -1 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]
												+ "' href='#modal-update'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;

									}
								}],
						"order" : [ [ 1, "des" ] ]

					}
				});

		// handle group actionsubmit button click
		grid.getTableWrapper().on(
				'click',
				'.table-group-action-submit',
				function(e) {
					e.preventDefault();
					var action = $(".table-group-action-input", grid
							.getTableWrapper());
					if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
						grid.setAjaxParam("customActionType", "group_action");
						grid.setAjaxParam("customActionName", action.val());
						grid.setAjaxParam("id", grid.getSelectedRows());
						grid.getDataTable().ajax.reload();
						grid.clearAjaxParams();
					} else if (action.val() == "") {
						App.alert({
							type : 'danger',
							icon : 'warning',
							message : 'Please select an action',
							container : grid.getTableWrapper(),
							place : 'prepend'
						});
					} else if (grid.getSelectedRowsCount() === 0) {
						App.alert({
							type : 'danger',
							icon : 'warning',
							message : 'No record selected',
							container : grid.getTableWrapper(),
							place : 'prepend'
						});
					}
				});

	}
	
	var getId = function() {
		debugger;
		$('#codParcelaR').empty();
		var codpa = "";
		$("#modal-update").on(
				'show.bs.modal',
				function(e) {
					
					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					console.log(id);
					var getSp = {
						SP: "sp_getBloqueoById",
						FILTERS: {
							p_id: id
						}
					}
					console.log(getSp);
					$.ajax({
						url : "/HortiSafeMX/json/general/callSp",
						type : "PUT",
						data : JSON.stringify(getSp),
						async: false,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Accept","application/json");
							xhr.setRequestHeader("Content-Type","application/json");
						},
						success: function(res){
							console.log(res)
							console.log(res.data[0])
							var data = res.data[0];
							var idMercado = data.mercado.split(" , ");
							data.idMercado = idMercado;
							var idVariedad = data.variedad.split(" , ");
							data.idVariedad = idVariedad;
							codpa = data.codParcela;
							console.log(data)
							var codturno_ = data.codTurno;
							var idEspecie_ = data.idEspecie;
							debugger;
							for (var k in data) {
						       //alert(k  +" ->" +data[k]);
						       $("#"+k+"u").val(data[k]).trigger("change");
						    }
						       
						       
						       $('#codParcelaR').empty();
								$('#codParcelaR').append('<option value="">Seleccionar</option>');
								var productor_ = data.codProductor;
								$.ajax({
									url : "/HortiSafeMX/"+"json/parcela/getAllByProductor/"+productor_,
									type : "GET",
									data : "",
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										var options = "";
										debugger;
										$(data).each(function(key, val){
											if( val.codigo == parseInt(codpa)){
												options += "<option value='"+val.codigo+"' selected>"+val.nombre+"</option>";
											}else{
												options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
											}
											
										})
									
										$('#codParcelaR').append(options);   
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								}); 
								
								
								$('#codTurnoR').empty();
								$('#codTurnoR').append('<option value="">Seleccionar</option>');
								var productor=$('#codTurnoR').children("option:selected").val();
								
								$.ajax({
									url : "/HortiSafeMX/"+"json/sector/getAllByParcela/"+productor_+"/"+parseInt(codpa),
									type : "GET",
									data : "",
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										var options = "";
										
										$(data).each(function(key, val){
											
											if(parseInt(codturno_) == parseInt(val.codTurno)){
												options += "<option value='"+val.codTurno+"' selected>"+val.nombre+"</option>";
											}else{
												options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
											}
											
										})
									
										$('#codTurnoR').append(options);
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});
								
								
								
								$('#idEspecieu').empty();
								$('#idEspecieu').append('<option value="">Seleccionar</option>');
								
								
								$.ajax({
									url : "/HortiSafeMX/"+"json/especie/get2/"+productor_+"/"+parseInt(codpa)+"/"+codturno_,
									type : "GET",
									data : "",
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},
									success : function(data, textStatus, jqXHR) {
										var options = "";
										
										
										$(data).each(function(key, val){
											if(parseInt(idEspecie_) == parseInt(val.idEspecie)){
												options += "<option value='"+val.idEspecie+"' selected>"+val.especie+"</option>";

											}else{
												options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";

											}
										})
									
										$('#idEspecieu').append(options);
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});
								
								
								var getV = {
										TABLE: "variedad",
										WHERE: {
											idEspecie: parseInt(idEspecie_)
										},
										ORDER: {
											BY: ["nombre"]
										}
									}
									console.log(getV);
									$.ajax({
										url : "/HortiSafeMX/json/general/select",
										type : "PUT",
										data : JSON.stringify(getV),
										async: false,
										beforeSend : function(xhr) {
											xhr.setRequestHeader("Accept","application/json");
											xhr.setRequestHeader("Content-Type","application/json");
										},
										success: function(res){
											var opt = "<option value='-1'>Todas</option>";
											$.each(res.data, function(k,v){
												opt += "<option value='"+v.idVariedad+"'>"+v.cod+" | "+v.nombre+"</option>";
											})
											$(".variedad2").html(opt);
										},error: function(e){
											console.log(e)
										},complete:function(){
										}
									})
								
									$("#idVariedadu").val(parseInt(data.idVariedad)).trigger("change");
								
								
						       
						       
						},error: function(e){
							console.log(e)
						},complete:function(){
						}
					})
//					$.ajax({
//						type : 'GET',
//						url : "/HortiSafeMX/json/bloqueo/" + id,
//						data : "",
//						success : function(dat-a) {
//							console.log(data)
//						
//							
//							for (var k in data) {
//						       //alert(k  +" ->" +data[k]);
//						       $("#"+k+"u").val(data[k]).trigger("change");
//						    }
//						}
//					});
				});
		
		$('#codParcelaR').val(parseInt(codpa));
	}

	var update = function() {

		var row = {};
		var form1 = $('#form-update');

		form1.validate({
			errorElement : 'span', 
			errorClass : 'help-block help-block-error',
			focusInvalid : true, 
			rules : {
				updateTipoProducto : {
					required : true,
					rangelength : [ 5, 50 ],
					alfanumerico : true
				}
			},
			messages : {
				updateTipoProducto : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 5 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						}
					},
					errorPlacement : function(error, element) { 
						if (element.parent(".input-group").size() > 0) {
							error.insertAfter(element.parent(".input-group"));
						} else if (element.attr("data-error-container")) {
							error
									.appendTo(element
											.attr("data-error-container"));
						} else if (element.parents('.radio-list').size() > 0) {
							error.appendTo(element.parents('.radio-list').attr(
									"data-error-container"));
						} else if (element.parents('.radio-inline').size() > 0) {
							error.appendTo(element.parents('.radio-inline')
									.attr("data-error-container"));
						} else if (element.parents('.checkbox-list').size() > 0) {
							error.appendTo(element.parents('.checkbox-list')
									.attr("data-error-container"));
						} else if (element.parents('.checkbox-inline').size() > 0) {
							error.appendTo(element.parents('.checkbox-inline')
									.attr("data-error-container"));
						} else {
							error.insertAfter(element);
						}
					},

					invalidHandler : function(event, validator) { 
						App
								.alert({
									container : '#modal-modifica-cuenta .modal-body', 
									place : 'prepend', // append or prepent in
														// container
									type : 'danger', // alert's type
									message : 'Por favor Corrija los errores antes de continuar', // alert's
																									// message
									close : false, // make alert closable
									reset : true, // close all previouse
													// alerts first
									focus : false, // auto scroll to the alert
													// after shown
									closeInSeconds : 5
								});
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error');
					},

					unhighlight : function(element) { // revert the change
														// done by hightlight
						$(element).closest('.form-group').removeClass(
								'has-error'); // set error class to the
												// control group
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); 
					},

					submitHandler : function(form) {


						// parametrosCuenta.Cuenta = cuenta;
						debugger;
						var obj = $('#form-update').serializeObject();
						obj.idBloqueo=ID;
						var idVariedad = $("#idVariedadu").val()
						var variedades = "";
						for(var i = 0; i < idVariedad.length; i++){
							variedades += idVariedad[i]+",";
						}
						obj.variedades = variedades;
						var idMercado = $("#idMercadou").val()
						var mercados = "";
						for(var i = 0; i < idMercado.length; i++){
							mercados += idMercado[i]+",";
						}
						obj.mercados = mercados;
						obj.idMercado = 0;
						obj.idVariedad = 0;
						var search = JSON.stringify(obj);
						 
						
						console.log(search);
						$.ajax({
									url : "/HortiSafeMX/"+"json/bloqueo/put",
									type : "PUT",
									data : search,
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-update').modal("toggle");
										swal({
											  title: 'Tipo Producto Modificada exitosamente!',
											  animation: true,
											  customClass: 'animated tada'
											})
											
										var table = $('#datatable_ajax')
												.DataTable({
													bRetrieve : true
												});
										table.ajax.reload();
										
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});

					}

				});

	}
	
	var insert = function(){
		var row = {};
		var form1 = $('#form-new');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						codProductor : {required : true},
						idMercado : {required : true},
						idEspecie : {required : true},
						b : {required : true},
						activo : {required : true},
					},
					messages : {
						codProductor : {required: "Este campo es obligatorio"},
						idMercado : {required: "Este campo es obligatorio"},
						idEspecie : {required: "Este campo es obligatorio"},
						b : {required: "Este campo es obligatorio"},
						activo : {required: "Este campo es obligatorio"},
					},
					errorPlacement : function(error, element) { 
						if (element.parent(".input-group").size() > 0) {
							error.insertAfter(element.parent(".input-group"));
						} else if (element.attr("data-error-container")) {
							error.appendTo(element.attr("data-error-container"));
						} else if (element.parents('.radio-list').size() > 0) {
							error.appendTo(element.parents('.radio-list').attr("data-error-container"));
						} else if (element.parents('.radio-inline').size() > 0) {
							error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
						} else if (element.parents('.checkbox-list').size() > 0) {
							error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
						} else if (element.parents('.checkbox-inline').size() > 0) {
							error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
						} else {
							error.insertAfter(element); 
						}
					},
					invalidHandler : function(event, validator) { 
					},
					highlight : function(element) {
						$(element).closest('.form-group').addClass('has-error'); 
					},
					unhighlight : function(element) { 
						$(element).closest('.form-group').removeClass('has-error');
					},
					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); 
					},
					submitHandler : function(form) {
						row.tipoProducto = $('#regTipoProducto').val();
						row.idUser = "asasas";
						row.codParcela = $('#codParcela').val();
						row.codTurno = $('#codTurno').val();
						
						var idVariedad = $("#idVariedad").val()
						var variedades = "";
						for(var i = 0; i < idVariedad.length; i++){
							variedades += idVariedad[i]+",";
						}
						var obj = $('#form-new').serializeObject();
						obj.variedades = variedades;
						var idMercado = $("#idMercado").val()
						var mercados = "";
						for(var i = 0; i < idMercado.length; i++){
							mercados += idMercado[i]+",";
						}
						obj.mercados = mercados;
						obj.idMercado = 0;
						obj.idVariedad = 0;
						var search = JSON.stringify(obj);
						$.ajax({
							url : "/HortiSafeMX/"+"json/bloqueo/add",
							type : "PUT",
							data : search,
							dataType : "text",
							beforeSend : function(xhr) {
							xhr.setRequestHeader("Accept","application/json");
							xhr.setRequestHeader("Content-Type","application/json");
						},
						success : function(data, textStatus, jqXHR) {
							$('#modal-insert').modal('toggle');
							swal({
								title: 'Bloqueo de productor realizado exitosamente',
								animation: true,
								customClass: 'animated tada'
							})
							$( ".form-control" ).each(function( index ) {
								$( this ).val("");
							});
							$("#activo").val("Y");
							var table = $('#datatable_ajax').DataTable({
								bRetrieve : true
							});
							table.ajax.reload();
						},
						error : function(jqXHR, textStatus,errorThrown) {
							console.log("error");
						}
					});
				}
			});
	}
	return {
		init : function() {
		populateForm();//recarga los select, check box, radio
		getView(); //metodo encargado trabajo con la grilla
			
		insert();//metodo de validacion y envio a funcion de insercion
			
		getId();//obtiene los datos atraves de ajax para su edicion
		update();//metodo de validaciob y envio a funcion de edicion
			
			//Agregar metodos de validacion a libreria
		jQuery.validator.addMethod("alfanumerico",
		function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+$/.test(value);
		}, "solo números ddddd");
			
			$.validator.addMethod("repetidos", function(value, element) {
				return !this.optional(element) && !this.optional($(element).parent().prev().children("select")[0]);
			}, "Please select both the item and its amount.");
		}
	};
}();
$(".especie2").change(function(){
	var getV = {
		TABLE: "variedad",
		WHERE: {
			idEspecie: $(this).val()
		},
		ORDER: {
			BY: ["nombre"]
		}
	}
	console.log(getV);
	$.ajax({
		url : "/HortiSafeMX/json/general/select",
		type : "PUT",
		data : JSON.stringify(getV),
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(res){
			var opt = "<option value='-1'>Todas</option>";
			$.each(res.data, function(k,v){
				opt += "<option value='"+v.idVariedad+"'>"+v.cod+" | "+v.nombre+"</option>";
			})
			$(".variedad").html(opt);
		},error: function(e){
			console.log(e)
		},complete:function(){
		}
	})
})


$("#idEspecieu").change(function(){
	var getV = {
		TABLE: "variedad",
		WHERE: {
			idEspecie: $(this).val()
		},
		ORDER: {
			BY: ["nombre"]
		}
	}
	console.log(getV);
	$.ajax({
		url : "/HortiSafeMX/json/general/select",
		type : "PUT",
		data : JSON.stringify(getV),
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(res){
			var opt = "<option value='-1'>Todas</option>";
			$.each(res.data, function(k,v){
				opt += "<option value='"+v.idVariedad+"'>"+v.cod+" | "+v.nombre+"</option>";
			})
			$("#idVariedadu").html(opt);
		},error: function(e){
			console.log(e)
		},complete:function(){
		}
	})
})

$(".especie").change(function(){
	var getV = {
		TABLE: "variedad",
		WHERE: {
			idEspecie: $(this).val()
		},
		ORDER: {
			BY: ["nombre"]
		}
	}
	console.log(getV);
	$.ajax({
		url : "/HortiSafeMX/json/general/select",
		type : "PUT",
		data : JSON.stringify(getV),
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(res){
			var opt = "<option value='-1'>Todas</option>";
			$.each(res.data, function(k,v){
				opt += "<option value='"+v.idVariedad+"'>"+v.cod+" | "+v.nombre+"</option>";
			})
			$(".variedad").html(opt);
		},error: function(e){
			console.log(e)
		},complete:function(){
		}
	})
})

$('#codProductor').on('change', function() {
		$('.codParcela').empty();
		$('.codParcela').append('<option value="">Seleccionar</option>');
		$.ajax({
			url : "/HortiSafeMX/"+"json/parcela/getAllByProductor/"+this.value,
			type : "GET",
			data : "",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},

			success : function(data, textStatus, jqXHR) {
				var options = "";
				
				$(data).each(function(key, val){
					options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
				})
			
				$('.codParcela').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	   
	  });


$('#codProductoru').on('change', function() {
	$('#codParcelaR').empty();
	$('#codTurnoR').empty();
	$('#codParcelaR').append('<option value="">Seleccionar</option>');
	$.ajax({
		url : "/HortiSafeMX/"+"json/parcela/getAllByProductor/"+this.value,
		type : "GET",
		data : "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept",
					"application/json");
			xhr.setRequestHeader("Content-Type",
					"application/json");
		},

		success : function(data, textStatus, jqXHR) {
			var options = "";
			
			$(data).each(function(key, val){
				options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
			})
		
			$('#codParcelaR').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });

$('#codParcela').on('change', function() {
	
	$('.codTurno').empty();
	$('.codTurno').append('<option value="">Seleccionar</option>');
	var productor=$('#codProductor').children("option:selected").val();
	
	$.ajax({
		url : "/HortiSafeMX/"+"json/sector/getAllByParcela/"+productor+"/"+this.value,
		type : "GET",
		data : "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept",
					"application/json");
			xhr.setRequestHeader("Content-Type",
					"application/json");
		},

		success : function(data, textStatus, jqXHR) {
			var options = "";
			
			$(data).each(function(key, val){
				options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
			})
		
			$('.codTurno').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
  });


$('#codParcelaR').on('change', function() {
	debugger;
	$('#codTurnoR').empty();
	$('#codTurnoR').append('<option value="">Seleccionar</option>');
	var productor=$('#codProductor').children("option:selected").val();
	
	$.ajax({
		url : "/HortiSafeMX/"+"json/sector/getAllByParcela/"+$('#codProductoru').val()+"/"+this.value,
		type : "GET",
		data : "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept",
					"application/json");
			xhr.setRequestHeader("Content-Type",
					"application/json");
		},

		success : function(data, textStatus, jqXHR) {
			var options = "";
			
			$(data).each(function(key, val){
				options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
			})
		
			$('#codTurnoR').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
  });
jQuery(document).ready(function() {
	controllerPage.init();
	$("#modal-new").click(function() {
		$("#codProductor").val("");
		$("#codParcela").val("");
		$("#codTurno").val("");
		$("#idEspecie").val("");
		$("#comentario").val("");
		$("#b").val("");
		$('#idMercado').val(null).trigger('change');
		$('#idVariedad').val(null).trigger('change');
		
	});
});

$('#codTurno').on('change', function() {
	$('.especie2').empty();
	$('.especie2').append('<option value="">Seleccionar</option>');
	
	var productor=$('#codProductor').children("option:selected").val();
	var codParcela=$('#codParcela').children("option:selected").val();
	var codTurno=$('#codTurno').children("option:selected").val();
	$.ajax({
		url : "/HortiSafeMX/"+"json/especie/get2/"+productor+"/"+codParcela+"/"+codTurno,
		type : "GET",
		data : "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept",
					"application/json");
			xhr.setRequestHeader("Content-Type",
					"application/json");
		},
		success : function(data, textStatus, jqXHR) {
			var options = "";
			
			$(data).each(function(key, val){
				options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";
			})
		
			$('.especie2').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
  });


$('#codTurnoR').on('change', function() {
	$('#idEspecieu').empty();
	$('#idEspecieu').append('<option value="">Seleccionar</option>');
	
	var productor=$('#codProductoru').children("option:selected").val();
	var codParcela=$('#codParcelaR').children("option:selected").val();
	var codTurno=$('#codTurnoR').children("option:selected").val();
	$.ajax({
		url : "/HortiSafeMX/"+"json/especie/get2/"+productor+"/"+codParcela+"/"+codTurno,
		type : "GET",
		data : "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept",
					"application/json");
			xhr.setRequestHeader("Content-Type",
					"application/json");
		},
		success : function(data, textStatus, jqXHR) {
			var options = "";
			
			$(data).each(function(key, val){
				options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";
			})
		
			$('#idEspecieu').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
  });

