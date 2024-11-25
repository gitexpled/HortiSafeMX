var TableDatatablesAjax = function() {
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
							"url" : "/HortiSafeMX/"+"json/parcela/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 5 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a style='width:100%;' class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
											+ full[0]+"/"+ full[1]
												+ "' href='#modal-modifica-productor'><i class='fa fa-pencil-square'></i></a> ";

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
		// grid.setAjaxParam("customActionType", "group_action");
		// grid.getDataTable().ajax.reload();
		// grid.clearAjaxParams();
	}

	var setEstado = function(){
		$("body").on("click",".changeStatus",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/HortiSafeMX/"+"json/user/setStatus",
				data : {
					id: id
				},
				success : function(data) {
					swal({
						  title: 'Usuario Modificado exitosamente!',
						  animation: true,
						  customClass: 'animated tada'
						})
					var table = $('#datatable_ajax').DataTable({
								bRetrieve : true
					});
					table.ajax.reload();
				}
			});
		})
	}
	var limpiar = function() {
		$("#modal-newProductor").on(
				'show.bs.modal',
				function(e) {

					 $('#regCodigo').val("");
					$('#codigoProductor').val("");
					$('#regNombre').val("");
				
				});
	}

	var obtener = function() {
		$("#modal-modifica-productor").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/HortiSafeMX/json/parcela/" + id,
						data : "",
						success : function(data) {
							$("#updateUser").val(data.codigo);
							$("#updateNombre").val(data.nombre);
							$("#updateFeCreacion").val(data.creado);
							$("#updateModificacion").val(data.modificado);
							$("#updateProductor").val(data.codigoProductor);
						
								
							
							$.ajax({
								type: 'GET',
								url : "/HortiSafeMX/json/user/"+data.idUser,
								data:"",
								success: function(user){
									$('#updateUserMod').val(user.user);
								}
							})
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
						
						updateNombre : {
							required : true,
							rangelength : [ 1, 50 ]
						}

					},

					messages : {

						updateNombre : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 5 y menor a 50",
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

						row.codigo = $('#updateUser').val();;
						row.nombre = $("#updateNombre").val();
						row.idUser = $("#idUserPefil").val();
						row.codigoProductor = $('#updateProductor').val();;
						
						$.ajax({
									url : "/HortiSafeMX/"+"json/parcela/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica-productor').modal("toggle");
										swal({
											  title: 'Productor Modificado exitosamente!',
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
	
	var insertProductor = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertProductor');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regCodigo : {
							required : true,
							rangelength : [ 1, 6 ]
						},
						regNombre : {
							required : true,
							rangelength : [ 1, 50 ]
						},
						codigoProductor : {
							required : true
						}

					},

					messages : {
						codigoProductor : {
							required: "Este campo es obligatorio"
						},
						regCodigo : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 1 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						regNombre : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 1 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
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

						row.codigo = $('#regCodigo').val();
						row.nombre = $('#regNombre').val();
						row.idUser = $("#idUserPefil").val();
						row.codigoProductor = $('#codigoProductor').val();
						

						$.ajax({
									url : "/HortiSafeMX/"+"json/parcela/insert",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newProductor').modal('toggle');
									swal({
										  title: 'Productor ingresado exitosamente!',
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
										console.log("error");

									}
								});
					}

				});
	}

	return {

		// main function to initiate the module
		init : function() {
			
			limpiar();
			handleDemo1();
			insertProductor();
			editar();
			setEstado();
			obtener();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /[a-zA-Z]/.test(value);
					}, "solo números ddddd");
		}

	};

}();

jQuery(document).ready(function() {
	TableDatatablesAjax.init();
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
				options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
			})
		
			$('.productor').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
});