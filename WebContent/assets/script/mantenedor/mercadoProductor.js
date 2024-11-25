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
							"url" : "/HortiSafeMX/"+"json/mercadoProductor/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 7 ],
									"render" : function(data, type, full) {
										var html = "<div class='btn-group pull-left  btn-group-sm'>";

										html += "<a onclick='' class='col-md-2 btn red btn-table pull-right button-grilla-elimina-cuenta changeStatus'   data-id='"
												+ full[0]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";

									

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
				url : "/HortiSafeMX/"+"json/mercadoProductor/delete",
				data : {
					id: id
				},
				success : function(data) {
					swal({
						  title: 'Registro eliminado exitosamente!',
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

					 $('#codParcela').val("");
					$('#codVariedad').val("");
					
				
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
						url : "/HortiSafeMX/json/mercadoProductor/" + id,
						data : "",
						success : function(data) {
							console.log(data);
							$("#updatecodParcela").val(data.codParcela);
							$("#updatecodVariedad").val(data.idVariedad);
							$("#updateFeCreacion").val(data.creado);
							$("#updateModificacion").val(data.modificado);
							$("#updateId").val(id);
							
								
							
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
						
						codParcela : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
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

						row.idVariedad = $('#updatecodVariedad').val();;
						row.codParcela = $("#updatecodParcela").val();

						row.id = $('#updateId').val();;
						
						$.ajax({
									url : "/HortiSafeMX/"+"json/mercadoProductor/put",
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
	
	var insert = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertProductor');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						codProductor : {
							required : true,
							
						},
						codParcela : {
							required : true,
							
						},
						codTurno : {
							required : true,
							
						},
						idVariedad : {
							required : true,
							
						},
						idMercado : {
							required : true,
							
						}

					},

					messages : {
						codigoProductor : {
							required: "Este campo es obligatorio"
						},
						codParcela : {
							required: "Este campo es obligatorio"
						},
						codTurno : {
							required: "Este campo es obligatorio"
						},
						idVariedad : {
							required: "Este campo es obligatorio"
						},
						idMercado : {
							required: "Este campo es obligatorio"
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

						row.codProductor = $('#codProductor').val();
						row.codParcela = $('#codParcela').val();
						row.codTurno = $('#codTurno').val();
						row.idVariedad = $('#idVariedad').val();
						row.idMercado = $('#idMercado').val();
						
						

						$.ajax({
									url : "/HortiSafeMX/"+"json/mercadoProductor/insert",
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
										  title: 'parcela variedad ingresado exitosamente!',
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
			insert();
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
				options += "<option value='"+val.codigo+"'>"+val.codigo+" "+val.nombre+"</option>";
			})
		
			$('.codProductor').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
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
	$('#codParcela').on('change', function() {
		$('.idEspecie').empty();
		$('.idEspecie').append('<option value="">Seleccionar</option>');
		
		var productor=$('#codProductor').children("option:selected").val();
		var codParcela=$('#codParcela').children("option:selected").val();
		var codTurno=$('#codTurno').children("option:selected").val();
		$.ajax({
			url : "/HortiSafeMX/"+"json/especie/get/"+productor+"/"+codParcela+"/"+codTurno,
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
			
				$('.idEspecie').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	   
	  });
	$('#codTurno').on('change', function() {
		$('.idVariedad').empty();
		$('.idVariedad').append('<option value="">Seleccionar</option>');
		codParcela=$('#codParcela').val();
		
	$.ajax({
		url : "/HortiSafeMX/"+"json/variedad/getAllByTurno/"+codParcela+"/"+this.value,
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
				options += "<option value='"+val.cod+"'>"+val.nombre+"</option>";
			})
		
			$('.idVariedad').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
	 });
	
});