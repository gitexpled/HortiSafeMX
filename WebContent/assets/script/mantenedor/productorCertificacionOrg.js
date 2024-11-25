var TableDatatablesAjax = function() {
	var initPickers = function() {
		// init date pickers
		$('.date-picker').datepicker({
			rtl : App.isRTL(),
			autoclose : true,
			 format: 'yyyy-mm-dd'
		});
	}
	var handleDemo1 = function() {

		var grid = new Datatable();

		grid.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {
						// grid: grid object
						// response: json object of server side ajax response
						// execute some code after table records loaded
					},
					onError : function(grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(grid) {
						// execute some code on ajax data load
					},
					loadingMessage : 'Loading...',
					dataTable : { 

						"bStateSave" : true, // save datatable
												// state(pagination, sort, etc)
												// in cookie.
					
						"lengthMenu" : [ [ 10, 20, 50, 100, 150, -1 ],
								[ 10, 20, 50, 100, 150, "All" ] // change per
																// page values
																// here
						],
						"pageLength" : 20, // default record count per page
						"ajax" : {
							"url" : "/HortiSafeMX/"+"json/ProductorCertificacionOrg/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 6 ],
									"render" : function(data, type, full) {
										console.log(full);
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";

										html += "<a style='width:100%;' class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[6]
												+ "' href='#modal-modifica-CertificacionProd'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;
									}
								}],
						"order" : [ [ 1, "asc" ] ]
					// set first column as a default sort by asc

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


	
	var obtener = function() {
		$("#modal-modifica-CertificacionProd").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
				
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/HortiSafeMX/json/ProductorCertificacionOrg/" + id,
						data : "",
						success : function(data) {
						
						
							var idMercado=data.idMercado;
							var idEspecie=data.idEspecie;
							var idCertificacion=data.idCertificacion;
							console.log(data);
							$("#updatecodProd").val(data.codProductor);
							$("#codParcelaUpdate").val(data.codParcela);
							var codParcela = data.codParcela;
							
							$('.codParcelaUpdate').empty();
							$('.codParcelaUpdate').append('<option value="">Seleccionar</option>');
							$.ajax({
								url : "/HortiSafeMX/"+"json/parcela/getAllByProductor/"+data.codProductor,
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
										if (val.codigo==codParcela)
											options += "<option value='"+val.codigo+"'  selected='selected'>"+val.nombre+"</option>";
										else
											options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
									})
								
									$('.codParcelaUpdate').append(options);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								}
							});
							
								
							$('.udpMercado').empty();
							$('.udpMercado').append('<option value="">Seleccionar</option>');
					
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
									if (val.idMercado==idMercado)
									options += "<option value='"+val.idMercado+"'   selected='selected'>"+val.mercado+"</option>";
									else
									options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
								})
								$('.udpMercado').append(options);
							},
							error : function(jqXHR, textStatus,
									errorThrown) {
							}
						});
						
						
						$('#udpEspecie').empty();
						$('#udpEspecie').append('<option value="">Seleccionar</option>');
						$.ajax({
							url : "/HortiSafeMX/"+"json/certificacionOrg/getEspecie/"+idMercado,
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
									
									if (val.idEspecie==idEspecie)
									options += "<option value='"+val.idEspecie+"'   selected='selected'>"+val.especie+"</option>";
									else
									options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";
									
								})
							
								$('#udpEspecie').append(options);
							},
							error : function(jqXHR, textStatus,
									errorThrown) {
							}
						});
						$('#updateCertificacion').empty();
						$('#updateCertificacion').append('<option value="">Seleccionar</option>');
						
						$.ajax({
							url : "/HortiSafeMX/"+"json/certificacionOrg/getCertificado/"+idMercado+"/"+idEspecie,
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
								console.log(data);
								$(data).each(function(key, val){
									
									
									if (val.idCertificaciones==idCertificacion)
									options += "<option value='"+val.idCertificaciones+"'   selected='selected'>"+val.certificacionesCol+"</option>";
									else
									options += "<option value='"+val.idCertificaciones+"'>"+val.certificacionesCol+"</option>";
								})
							
								$('#updateCertificacion').append(options);
							},
							error : function(jqXHR, textStatus,
									errorThrown) {
							}
						});
							
							$("#updateCertificacion").val(data.idCertificacion);
							$('#updateVigencia').val(data.vigencia);
						}
					});
					
					
					
					
					
				});
	}

	var editar = function() {

		var row = {};

		var form1 = $('#modifica-cuenta-form');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						updateTipoProducto : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						}

					},

					messages : {

						updateTipoProducto : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 4 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						}

					},

					errorPlacement : function(error, element) { // render error
																// placement for
																// each input
																// type
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

						row.codProductor = $('#updatecodProd').val();
						row.idCertificacion = $('#updateCertificacion').val();
						row.codParcela = $('#codParcelaUpdate').val();
						row.vigencia = $("#updateVigencia").val();
						
						row.idMercado = $("#udpMercado").val();
						row.idEspecie = $("#udpEspecie").val();
						
						row.idCert = ID;
						console.log(row);
						$.ajax({
									url : "/HortiSafeMX/"+"json/ProductorCertificacionOrg/putx",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica-CertificacionProd').modal("toggle");
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
	
	var insertTipoProducto = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertTipoProducto');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regCodigoProductor : {
							required : true
						},
						codParcela : {
							required : true
						},
						regMercado : {
							required : true
						},
						regEspecie : {
							required : true
						},
						
						regVigencia : {
							required : true
						},
						regCertificacion : {
							required : true,
							remote: {
								url: "/HortiSafeMX/"+"json/ProductorCertificacionOrg/validaCertificacion",
								type: "POST",
								data: {
									codProd: function(){
										return $('#regCodigoProductor').val();
									},
									idCertificacion : function(){
										return $('#regCertificacion').val();
									}
									,
									idMercado : function(){
										return $('#regMercado').val();
									}
									,
									idEspecie : function(){
										return $('#regEspecie').val();
									}
								}
							}
						}

					},

					messages : {

						regCodigoProductor : {
							required: "Este campo es obligatorio",
							
						},
						codParcela : {
							required: "Este campo es obligatorio",
							
						},
						regCertificacion : {
							required: "Este campo es obligatorio",
							remote: "Este productor ya posee un certificado vigente"
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
							error.insertAfter(element); // for other inputs,
														// just perform default
														// behavior
						}
					},

					invalidHandler : function(event, validator) { // display
																	// error
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error'); 
					},

					unhighlight : function(element) { 
						$(element).closest('.form-group').removeClass(
								'has-error');
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); 
					},

					submitHandler : function(form) {

						row.codProductor = $('#regCodigoProductor').val();
						row.codParcela = $('#codParcela').val();
						row.idCertificacion = $('#regCertificacion').val();
						row.vigencia = $('#regVigencia').val();
						row.idMercado = $('#regMercado').val();
						row.idEspecie = $('#regEspecie').val();
						

						$.ajax({
									url : "/HortiSafeMX/"+"json/ProductorCertificacionOrg/insertProductorCertificacion",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newCertificacionProd').modal('toggle');
									swal({
										  title: 'Tipo Producto ingresada exitosamente!',
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
	
	var cargaExcel = function() {

		var row = {};

		var form1 = $('#cargaExcel');

		form1.validate({
			errorElement : 'span', 
			errorClass : 'help-block help-block-error',
			focusInvalid : true, 
			rules : {
				file : {
					required : true

				},
				

			},

			messages : {

				file : {
					required: "Seleccione el archivo de folios(CAF)"
					
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
					error.insertAfter(element); // for other inputs,
												// just perform default
												// behavior
				}
			},

			invalidHandler : function(event, validator) { // display
															// error
			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); 
			},

			unhighlight : function(element) { 
				$(element).closest('.form-group').removeClass(
						'has-error');
			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error'); 
			},

			submitHandler : function(form) {

				var oMyForm = new FormData();
				  oMyForm.append("file", file.files[0]);
				  oMyForm.append("hola","hola");
				

				$.ajax({
							url : "/HortiSafeMX/"+"json/ProductorCertificacionOrg/put",
							data: oMyForm,
						    dataType: 'text',
						    processData: false,
						    contentType: false,
						    type: 'POST',
							success : function(data, textStatus, jqXHR) {
								$('#modal-crea-folio').modal('toggle');
							swal({
								  title: 'Excel Cargado exitosamente!',
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
	var create = function() {
		$("#modal-crea-folio").on(
				'show.bs.modal',
				function(e) {
					$("#file").val("");
					
				});
			
	}		

	return {

		// main function to initiate the module
		init : function() {

			handleDemo1();
			insertTipoProducto();
			editar();
			obtener();
			initPickers();
			create();
			cargaExcel();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
		}

	};

}();


$('#regCodigoProductor').on('change', function() {
	$('#codParcela').empty();
	$('#codParcela').append('<option value="">Seleccionar</option>');
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
		
			$('#codParcela').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });

jQuery(document).ready(function() {
	
	$.getJSON("/HortiSafeMX/json/productor/getAllOutFilter",function(data){
		
		var option = "";
		option = "<option value='0'>Seleccionar</option>";
		$(data).each(function(key,val){
			
			option += "<option value='"+val.codigo+"'>"+val.codigo+"|"+val.nombre+"</option>";
		})
		$('.productorSelect').html(option);
	})
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
				$('.mercados').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	TableDatatablesAjax.init();
});

$('#updatecodProd').on('change', function() {
	$('.codParcelaUpdate').empty();
	$('.codParcelaUpdate').append('<option value="">Seleccionar</option>');
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
		
			$('.codParcelaUpdate').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });

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
  
  
  $('#regMercado').on('change', function() {
	$('#regEspecie').empty();
	$('#regEspecie').append('<option value="">Seleccionar</option>');
	$.ajax({
		url : "/HortiSafeMX/"+"json/certificacionOrg/getEspecie/"+this.value,
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
		
			$('#regEspecie').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
  $('#regEspecie').on('change', function() {
  
	$('#regCertificacion').empty();
	$('#regCertificacion').append('<option value="">Seleccionar</option>');
	var idMercado=$('#regMercado').val();
	$.ajax({
		url : "/HortiSafeMX/"+"json/certificacionOrg/getCertificado/"+idMercado+"/"+this.value,
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
			console.log(data);
			$(data).each(function(key, val){
				options += "<option value='"+val.idCertificaciones+"'>"+val.certificacionesCol+"</option>";
			})
		
			$('#regCertificacion').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
  
  
  
   $('#udpMercado').on('change', function() {
	$('#udpEspecie').empty();
	$('#udpEspecie').append('<option value="">Seleccionar</option>');
	$.ajax({
		url : "/HortiSafeMX/"+"json/certificacionOrg/getEspecie/"+this.value,
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
		
			$('#udpEspecie').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
  $('#udpEspecie').on('change', function() {
  
	$('#updateCertificacion').empty();
	$('#updateCertificacion').append('<option value="">Seleccionar</option>');
	var idMercado=$('#udpMercado').val();
	$.ajax({
		url : "/HortiSafeMX/"+"json/certificacionOrg/getCertificado/"+idMercado+"/"+this.value,
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
			console.log(data);
			$(data).each(function(key, val){
				options += "<option value='"+val.idCertificaciones+"'>"+val.certificacionesCol+"</option>";
			})
		
			$('#updateCertificacion').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  