<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="easyinsuranceApp.cliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">Create or edit a Cliente</h2>
        <div>
          <div class="form-group" v-if="cliente.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cliente.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="cliente-nomeCliente">Nome Cliente</label>
            <input
              type="text"
              class="form-control"
              name="nomeCliente"
              id="cliente-nomeCliente"
              data-cy="nomeCliente"
              :class="{ valid: !$v.cliente.nomeCliente.$invalid, invalid: $v.cliente.nomeCliente.$invalid }"
              v-model="$v.cliente.nomeCliente.$model"
              required
            />
            <div v-if="$v.cliente.nomeCliente.$anyDirty && $v.cliente.nomeCliente.$invalid">
              <small class="form-text text-danger" v-if="!$v.cliente.nomeCliente.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="cliente-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="cliente-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.cliente.usuario.$invalid, invalid: $v.cliente.usuario.$invalid }"
              v-model="$v.cliente.usuario.$model"
              required
            />
            <div v-if="$v.cliente.usuario.$anyDirty && $v.cliente.usuario.$invalid">
              <small class="form-text text-danger" v-if="!$v.cliente.usuario.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="cliente-dataNascimento">Data Nascimento</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="cliente-dataNascimento"
                  v-model="$v.cliente.dataNascimento.$model"
                  name="dataNascimento"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="cliente-dataNascimento"
                data-cy="dataNascimento"
                type="text"
                class="form-control"
                name="dataNascimento"
                :class="{ valid: !$v.cliente.dataNascimento.$invalid, invalid: $v.cliente.dataNascimento.$invalid }"
                v-model="$v.cliente.dataNascimento.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="cliente-foto">Foto</label>
            <div>
              <img
                v-bind:src="'data:' + cliente.fotoContentType + ';base64,' + cliente.foto"
                style="max-height: 100px"
                v-if="cliente.foto"
                alt="cliente image"
              />
              <div v-if="cliente.foto" class="form-text text-danger clearfix">
                <span class="pull-left">{{ cliente.fotoContentType }}, {{ byteSize(cliente.foto) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('foto', 'fotoContentType', 'file_foto')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_foto"
                id="file_foto"
                data-cy="foto"
                v-on:change="setFileData($event, cliente, 'foto', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="foto"
              id="cliente-foto"
              data-cy="foto"
              :class="{ valid: !$v.cliente.foto.$invalid, invalid: $v.cliente.foto.$invalid }"
              v-model="$v.cliente.foto.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="fotoContentType"
              id="cliente-fotoContentType"
              v-model="cliente.fotoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="cliente-telefone">Telefone</label>
            <input
              type="text"
              class="form-control"
              name="telefone"
              id="cliente-telefone"
              data-cy="telefone"
              :class="{ valid: !$v.cliente.telefone.$invalid, invalid: $v.cliente.telefone.$invalid }"
              v-model="$v.cliente.telefone.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.cliente.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cliente-update.component.ts"></script>
