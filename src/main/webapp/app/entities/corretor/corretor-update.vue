<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="easyinsuranceApp.corretor.home.createOrEditLabel" data-cy="CorretorCreateUpdateHeading">Create or edit a Corretor</h2>
        <div>
          <div class="form-group" v-if="corretor.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="corretor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="corretor-nomeCorretor">Nome Corretor</label>
            <input
              type="text"
              class="form-control"
              name="nomeCorretor"
              id="corretor-nomeCorretor"
              data-cy="nomeCorretor"
              :class="{ valid: !$v.corretor.nomeCorretor.$invalid, invalid: $v.corretor.nomeCorretor.$invalid }"
              v-model="$v.corretor.nomeCorretor.$model"
              required
            />
            <div v-if="$v.corretor.nomeCorretor.$anyDirty && $v.corretor.nomeCorretor.$invalid">
              <small class="form-text text-danger" v-if="!$v.corretor.nomeCorretor.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="corretor-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="corretor-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.corretor.usuario.$invalid, invalid: $v.corretor.usuario.$invalid }"
              v-model="$v.corretor.usuario.$model"
              required
            />
            <div v-if="$v.corretor.usuario.$anyDirty && $v.corretor.usuario.$invalid">
              <small class="form-text text-danger" v-if="!$v.corretor.usuario.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="corretor-dataNascimento">Data Nascimento</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="corretor-dataNascimento"
                  v-model="$v.corretor.dataNascimento.$model"
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
                id="corretor-dataNascimento"
                data-cy="dataNascimento"
                type="text"
                class="form-control"
                name="dataNascimento"
                :class="{ valid: !$v.corretor.dataNascimento.$invalid, invalid: $v.corretor.dataNascimento.$invalid }"
                v-model="$v.corretor.dataNascimento.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="corretor-foto">Foto</label>
            <div>
              <img
                v-bind:src="'data:' + corretor.fotoContentType + ';base64,' + corretor.foto"
                style="max-height: 100px"
                v-if="corretor.foto"
                alt="corretor image"
              />
              <div v-if="corretor.foto" class="form-text text-danger clearfix">
                <span class="pull-left">{{ corretor.fotoContentType }}, {{ byteSize(corretor.foto) }}</span>
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
                v-on:change="setFileData($event, corretor, 'foto', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="foto"
              id="corretor-foto"
              data-cy="foto"
              :class="{ valid: !$v.corretor.foto.$invalid, invalid: $v.corretor.foto.$invalid }"
              v-model="$v.corretor.foto.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="fotoContentType"
              id="corretor-fotoContentType"
              v-model="corretor.fotoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="corretor-telefone">Telefone</label>
            <input
              type="text"
              class="form-control"
              name="telefone"
              id="corretor-telefone"
              data-cy="telefone"
              :class="{ valid: !$v.corretor.telefone.$invalid, invalid: $v.corretor.telefone.$invalid }"
              v-model="$v.corretor.telefone.$model"
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
            :disabled="$v.corretor.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./corretor-update.component.ts"></script>
