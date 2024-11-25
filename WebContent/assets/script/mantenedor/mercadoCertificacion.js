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
							"url" : "/HortiSafeMX/"+"json/mercadoCertificacion/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 2 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";

										html += "<a style='width:100%;' class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[2]
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
						url : "/HortiSafeMX/json/mercadoCertificacion/" + id,
						data : "",
						success : function(data) {
							
							$("#idCertificacionu").val(data.idCertificacion);
							$("#idMercadou").val(data.idMercado);
							
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
						idMercadou : {
							required : true
						},
						idCertificacionu : {
							required : true/*,
							remote: {
								url: "/HortiSafeMX/"+"json/mercadoCertificacion/validaCertificacion",
								type: "POST",
								data: {
									idMercado: function(){
										return $('#idMercadou').val();
									},
									idCertificacion : function(){
										return $('#idCertificacionu').val();
									}
								}
							}*/
						}

					},

					messages : {

						idMercadou : {
							required: "Este campo es obligatorio",
							
						},
						idCertificacionu : {
							required: "Este campo es obligatorio",
							remote: "Este mercado ya posee un certificacion"
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

						row.idMercado = $('#idMercadou').val();
						row.idCertificacion = $('#idCertificacionu').val();
						row.id = ID;
						console.log(row);
						$.ajax({
									url : "/HortiSafeMX/"+"json/mercadoCertificacion/put",
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
											  title: 'Modificado exitosamente!',
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

		
		
		var form1 = $('#form-InsertTipoProducto');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						idMercado : {
							required : true
						},
						idCertificacion : {
							required : true,
							remote: {
								url: "/HortiSafeMX/"+"json/mercadoCertificacion/validaCertificacion",
								type: "POST",
								data: {
									idMercado: function(){
										return $('#idMercado').val();
									},
									idCertificacion : function(){
										return $('#idCertificacion').val();
									}
								}
							}
						}

					},

					messages : {

						idMercado : {
							required: "Este campo es obligatorio",
							
						},
						idCertificacion : {
							required: "Este campo es obligatorio",
							remote: "Este mercado ya posee un certificacion"
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

						row.idMercado = $('#idMercado').val();
						row.idCertificacion = $('#idCertificacion').val();
						row.id=0;
						

						$.ajax({
									url : "/HortiSafeMX/"+"json/mercadoCertificacion/insertmercadoCertificacion",
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
										  title: 'Ingresada exitosamente!',
										  animation: true,
										  customClass: 'animated tada'
										})
										$('#idMercado').val("");
										$('#idCertificacion').val("");
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
							url : "/HortiSafeMX/"+"json/mercadoCertificacion/put",
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
			insert();
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
$('.mercados').empty();
$('.mercados').append('<option value="">Seleccionar</option>');
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
	$.getJSON("/HortiSafeMX/json/certificacion/getAllOutFilter",function(data){
		var option = "";
		option = "<option value='0'>Seleccionar</option>";
		$(data).each(function(key,val){
			option += "<option value='"+val.idCertificaciones+"'>"+val.certificacionesCol+"</option>";
		})
		$('.certificacionSelect').html(option);
	})
	
	TableDatatablesAjax.init();
});