<template>
  <div>
    <h2 id="page-heading" data-cy="PropostaHeading">
      <span id="proposta-heading">Propostas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PropostaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-proposta"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Proposta </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && propostas && propostas.length === 0">
      <span>No propostas found</span>
    </div>
    <div class="table-responsive" v-if="propostas && propostas.length > 0">
      <table class="table table-striped" aria-describedby="propostas">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numeroProposta')">
              <span>Numero Proposta</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numeroProposta'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataProposta')">
              <span>Data Proposta</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataProposta'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('valorProposta')">
              <span>Valor Proposta</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valorProposta'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cliente.nomeCliente')">
              <span>Cliente</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cliente.nomeCliente'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('corretor.nomeCorretor')">
              <span>Corretor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'corretor.nomeCorretor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('plano.nomePlano')">
              <span>Plano</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'plano.nomePlano'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('seguradora.nomeSeguradora')">
              <span>Seguradora</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'seguradora.nomeSeguradora'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="proposta in propostas" :key="proposta.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PropostaView', params: { propostaId: proposta.id } }">{{ proposta.id }}</router-link>
            </td>
            <td>{{ proposta.numeroProposta }}</td>
            <td>{{ proposta.dataProposta }}</td>
            <td>{{ proposta.valorProposta }}</td>
            <td>{{ proposta.status }}</td>
            <td>
              <div v-if="proposta.cliente">
                <router-link :to="{ name: 'ClienteView', params: { clienteId: proposta.cliente.id } }">{{
                  proposta.cliente.nomeCliente
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="proposta.corretor">
                <router-link :to="{ name: 'CorretorView', params: { corretorId: proposta.corretor.id } }">{{
                  proposta.corretor.nomeCorretor
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="proposta.plano">
                <router-link :to="{ name: 'PlanoView', params: { planoId: proposta.plano.id } }">{{
                  proposta.plano.nomePlano
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="proposta.seguradora">
                <router-link :to="{ name: 'SeguradoraView', params: { seguradoraId: proposta.seguradora.id } }">{{
                  proposta.seguradora.nomeSeguradora
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PropostaView', params: { propostaId: proposta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PropostaEdit', params: { propostaId: proposta.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(proposta)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="easyinsuranceApp.proposta.delete.question" data-cy="propostaDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-proposta-heading">Are you sure you want to delete this Proposta?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-proposta"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeProposta()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./proposta.component.ts"></script>
