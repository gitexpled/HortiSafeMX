<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

					<div class="table-actions-wrapper">
					<div class="btn-group">
						
						
					</div>
					
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="20%">Cod. Productor</th>
							<th width="20%">Turno</th>
							<th width="20%">Proyecto</th>
							<th width="20%">Especie</th>
							<th width="20%">N° errores</th>
							
						
							<th width="20%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor">
							</td>
								<td>
								<input type="text"
								class="form-control form-filter input-sm" name="vw_nombreProductor">
							</td>
							
							<td></td><td></td><td></td>
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

<div id="modal-update" class="modal fade" tabindex="-1" aria-hidden="true"  >
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-update" class="horizontal-form" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Correción</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:400px; " data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                         
							
								<div class="row">
									<div class="col-md-2">
										<div class="form-group">
											<label class="control-label">Cod. Productor</label> <input
												type="text" name="codProductor" disabled="disabled" id="codProductor" class="form-control"
												placeholder="codProductor"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-5">
										<div class="form-group">
											<label class="control-label">Razón Social Informe</label> <input
												type="text" name="nombreProductor" disabled="disabled" id="nombreProductor" class="form-control"
												placeholder="nombre Productor"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-5">
										<div class="form-group">
											<label class="control-label">Razón Social Real</label> 
												<select name="codProductorNew" class="codProductor form-control" id="codProductorNew">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
								</div>


						


						</div>
						</div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="submit" class="btn green">Guardar</button>
            </div>
        </div>
        </div>
       </form>
    </div>
</div>