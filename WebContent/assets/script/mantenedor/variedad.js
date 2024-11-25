var TableDatatablesAjax = function() {
	var handleDemo1 = function() {

		var grid = new Datatable();
		var getT = {
			TABLE: "correoNotificacion"
		}
		grid.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {
						console.log(response)
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
							"url" : "/HortiSafeMX/"+"json/variedadM/viewSame", // ajax
						},
						"columnDefs" : [
								{
									"targets" : [3],
									"render" : function(data, type, full) {
										console.log(data);
										console.log(full);
										console.log(type);
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a style='width:100%;' class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[3]
												+ "' href='#modal-modifica-variedad'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;
									}
								} ],
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
		$("#modal-modifica-variedad").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					var get = {
						TABLE: "variedad",
						WHERE: {idVariedad: id}
					}
					$.ajax({
						url : "/HortiSafeMX/json/general/select",
						type : "PUT",
						data : JSON.stringify(get),
						async: false,
						success : function(res) {
							console.log(res)
							$("#idEspecieUpd").val(res.data[0].idEspecie).trigger("click");
							$("#codVariedadUpd").val(res.data[0].cod);
							$("#VariedadUpd").val(res.data[0].nombre);

						}
					});
				});
	}

	var editar = function() {

		var row = {};

		var form1 = $('#modifica-variedad-form');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
//					rules : {
//						updateCodProd : {
//							required : true,
//							rangelength : [ 3, 50 ]
//						},
//						updatecodRem : {
//							required : true,
//							rangelength : [ 3, 50 ],
//							alfanumerico : true,
//							remote: {
//								url: "/HortiSafeMX/"+"json/diccionario/validaDiccionario",
//								type: "POST",
//								data: {
//									diccionario: function(){
//										return $('#updatecodRem').val();
//									}
//								}
//							}
//						}
//
//					},

//					messages : {
//						updateCodProd : {
//							required : "Este campo es obligatorio",
//							rangelength : [ 3, 50 ]
//						},
//						updatecodRem : {
//							required : "Este campo es obligatorio",
//							rangelength : "Debe ser mayor a 5 y menor a 50",
//							alfanumerico : "Ingrese sólo valores alfanumericos",
//							remote: "Ya existe este código de reemplazo en el diccionario."
//						}
//
//					},

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

						row.codProducto = $('#updateCodProd').val();;
						row.codReemplazo = $("#updateCodProd").val();
						row.idDiccionario = ID;
						
						var u = {
							TABLE: "variedad",
							SET: {
								idEspecie: $("#idEspecieUpd").val(),
								cod: $("#codVariedadUpd").val(),
								nombre: $("#VariedadUpd").val()
							},
							WHERE: {idVariedad: ID}
						}
						$.ajax({
							url : "/HortiSafeMX/json/general/update",
							type : "PUT",
							data : JSON.stringify(u),
							async: false,
							beforeSend : function(xhr) {
								xhr.setRequestHeader("Accept","application/json");
								xhr.setRequestHeader("Content-Type","application/json");
							},
							success: function(data){
								$('#modal-modifica-variedad').modal("toggle");
								swal({
									  title: 'Diccionario Modificado exitosamente!',
									  animation: true,
									  customClass: 'animated tada'
									})
									$("#codVariedad").val("");
									$("#Variedad").val("");
									$("#idEspecie").val("");
								var table = $('#datatable_ajax')
										.DataTable({
											bRetrieve : true
										});
								table.ajax.reload();
							},error: function(e){
								console.log(e)
							},complete:function(){
							}
						})
						
//						$.ajax({
//									url : "/HortiSafeMX/"+"json/diccionario/put",
//									type : "PUT",
//									data : JSON.stringify(row),
//									beforeSend : function(xhr) {
//										xhr.setRequestHeader("Accept",
//												"application/json");
//										xhr.setRequestHeader("Content-Type",
//												"application/json");
//									},
//
//									success : function(data, textStatus, jqXHR) {
//										$('#modal-modifica-diccionario').modal("toggle");
//										swal({
//											  title: 'Diccionario Modificado exitosamente!',
//											  animation: true,
//											  customClass: 'animated tada'
//											})
//										var table = $('#datatable_ajax')
//												.DataTable({
//													bRetrieve : true
//												});
//										table.ajax.reload();
//										
//									},
//									error : function(jqXHR, textStatus,
//											errorThrown) {
//									}
//								});

					}

				});

	}
	
	var insertDiccionario = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertDiccionario');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
//					rules : {
//						regCodigoProd : {
//							required : true,
//							rangelength : [ 2, 50 ],
//							alfanumerico : true
//							},
//							regCodigoRem : {
//								required : true,
//								rangelength : [ 2, 50 ],
//								alfanumerico : true,
//								remote: {
//									url: "/HortiSafeMX/"+"json/diccionario/validaDiccionario",
//									type: "POST",
//									data: {
//										diccionario: function(){
//											return $('#regCodigoRem').val();
//										}
//									}
//							}
//						}
//					},

//					messages : {
//
//						regCodigoProd : {
//							required: "Este campo es obligatorio",
//							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
//							alfanumerico : "ingrese valores alfanumericos"
//						},
//						regCodigoRem : {
//							required: "Este campo es obligatorio",
//							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
//							alfanumerico : "ingrese valores alfanumericos",
//							remote: "Este código de producto ya existe en el diccionario"
//						}
//
//					},

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
						
						var g = {
							TABLE: "variedad",
							VALUES: {
								idEspecie: $("#idEspecie").val(),
								cod: $("#codVariedad").val(),
								nombre: $("#Variedad").val()
							}
						}
						$.ajax({
							url : "/HortiSafeMX/json/general/insert",
							type : "PUT",
							data : JSON.stringify(g),
							async: false,
							beforeSend : function(xhr) {
								xhr.setRequestHeader("Accept","application/json");
								xhr.setRequestHeader("Content-Type","application/json");
							},
							success: function(data){
								$('#modal-newMercado').modal('toggle');
								swal({
								  title: 'Correo ingresado exitosamente!',
								  animation: true,
								  customClass: 'animated tada'
								})
								var table = $('#datatable_ajax')
										.DataTable({
											bRetrieve : true
										});
								table.ajax.reload();
								$("#codVariedad").val("");
								$("#Variedad").val("");
								$("#idEspecie").val("");
							},error: function(e){
								console.log(e)
							},complete:function(){
							}
						})
					}

				});
	}

	return {

		// main function to initiate the module
		init : function() {

			handleDemo1();
			insertDiccionario();
			editar();
			obtener();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
		}

	};

}();

jQuery(document).ready(function() {
	TableDatatablesAjax.init();
	var g = {
		TABLE: "especie"
	}
	$.ajax({
		url : "/HortiSafeMX/json/general/select",
		type : "PUT",
		data : JSON.stringify(g),
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(res){
			var opt = "<option value=''>Seleccione</option>";
			$.each(res.data, function(k,v){
				opt += "<option value='"+v.idEspecie+"'>"+v.especie+"</option>";
			})
			$(".idEspecieSelect").html(opt);
		},error: function(e){
			console.log(e)
		},complete:function(){
		}
	})
//	Select(g).then(function(res){
//		
//	})
});