<template>
  <div>
    <h2 id="page-heading" data-cy="CorretorHeading">
      <span id="corretor-heading">Corretors</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'CorretorCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-corretor"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Corretor </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && corretors && corretors.length === 0">
      <span>No corretors found</span>
    </div>
    <div class="table-responsive" v-if="corretors && corretors.length > 0">
      <table class="table table-striped" aria-describedby="corretors">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nomeCorretor')">
              <span>Nome Corretor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nomeCorretor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span>Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataNascimento')">
              <span>Data Nascimento</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataNascimento'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('foto')">
              <span>Foto</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'foto'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('telefone')">
              <span>Telefone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'telefone'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="corretor in corretors" :key="corretor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CorretorView', params: { corretorId: corretor.id } }">{{ corretor.id }}</router-link>
            </td>
            <td>{{ corretor.nomeCorretor }}</td>
            <td>{{ corretor.usuario }}</td>
            <td>{{ corretor.dataNascimento }}</td>
            <td>
              <a v-if="corretor.foto" v-on:click="openFile(corretor.fotoContentType, corretor.foto)">
                <img
                  v-bind:src="'data:' + corretor.fotoContentType + ';base64,' + corretor.foto"
                  style="max-height: 30px"
                  alt="corretor image"
                />
              </a>
              <span v-if="corretor.foto">{{ corretor.fotoContentType }}, {{ byteSize(corretor.foto) }}</span>
            </td>
            <td>{{ corretor.telefone }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CorretorView', params: { corretorId: corretor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CorretorEdit', params: { corretorId: corretor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(corretor)"
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
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="easyinsuranceApp.corretor.delete.question" data-cy="corretorDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-corretor-heading">Are you sure you want to delete this Corretor?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-corretor"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeCorretor()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="corretors && corretors.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./corretor.component.ts"></script>
