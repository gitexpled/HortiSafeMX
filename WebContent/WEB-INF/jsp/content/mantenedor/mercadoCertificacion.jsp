<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newCertificacionProd" id="sample_editable_1_new" class="btn green  btn-outline">
							Nuevo Mercado Certificado <i class="fa fa-plus"></i>
						</button>
					
					</div>
					<!-- <div class="btn-group">
						<a class="btn red btn-outline " href="javascript:;"
							data-toggle="dropdown"> <i class="fa fa-share"></i> <span
							class="hidden-xs"> Herramientas </span> <i
							class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:;"> Export a Excel </a></li>

						</ul>
					</div> -->
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="20%">Mercado</th>
							<th width="20%">Certificación</th>
							
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><select class="form-control form-filter input-sm mercados" name="vw_idMercado"></select>
							</td>
						
							<td><select class="form-control form-filter input-sm certificacionSelect" name="vw_idCertificaciones"></select>
							</td>
							
							
							<td>
								<div class="margin-bottom-5">
									<div class="col-md-4">
									<button
										class="btn btn-sm green btn-outline filter-submit margin-bottom">
										<i class="fa fa-search"></i>
									</button>
									</div>
									<div class="col-md-6">
										<button class="col-6 btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> Borrar filtros
										</button>
									</div>
								</div>
								
							</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div id="modal-modifica-CertificacionProd" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<form id="modifica-cuenta-form">
			<div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <select
										id="idMercadou" name="idMercadou" class="form-control mercados"></select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Certificación: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <select 
										id="idCertificacionu" name="idCertificacionu" class="form-control certificacionSelect"></select> <br />
								</div>
							</div>
						</div>
					
						<div class="clearfix"></div>

					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn default">
							Cerrar <i class="fa fa-sign-out"></i>
						</button>
						<button type="submit" id="modificar-cuenta"
							class="btn gtd-blue-hard button-modifica-cuenta">
							Modificar <i class="fa fa-pencil-square"></i>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="modal-newCertificacionProd" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
       <form id="form-InsertTipoProducto" class="form-horizontal" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Nueva Certificación mercado</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-4 control-label">Mercado</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <select name="idMercado" id="idMercado" class="form-control mercados"></select> 
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">Certificación</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <select name="idCertificacion" id="idCertificacion"  class="form-control certificacionSelect"></select>
                                      </div>
                                </div>
                            </div>
                        </div>
                       
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="submit" class="btn green">Guardar</button>
            </div>
        </div>
       </form>
    </div>
</div>
<div id="modal-crea-folio" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<form id="cargaExcel" enctype="multipart/form-data">
			<div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Certificados: </label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i data-container="body" data-original-title="File"
									class="fa fa-info-circle tooltips"></i> <input type="file"
									id="file" name="file" value=""
									class="form-control"> <br />
							</div>
						</div>





						<div class="clearfix"></div>

					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn default">
							Cerrar <i class="fa fa-sign-out"></i>
						</button>
						<button type="submit" id="modificar-cuenta"
							class="btn gtd-blue-hard button-modifica-cuenta">
							Crear <i class="fa fa-pencil-square"></i>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
