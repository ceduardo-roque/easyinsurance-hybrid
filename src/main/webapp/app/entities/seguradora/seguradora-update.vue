<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="easyinsuranceApp.seguradora.home.createOrEditLabel" data-cy="SeguradoraCreateUpdateHeading">Create or edit a Seguradora</h2>
        <div>
          <div class="form-group" v-if="seguradora.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="seguradora.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="seguradora-nomeSeguradora">Nome Seguradora</label>
            <input
              type="text"
              class="form-control"
              name="nomeSeguradora"
              id="seguradora-nomeSeguradora"
              data-cy="nomeSeguradora"
              :class="{ valid: !$v.seguradora.nomeSeguradora.$invalid, invalid: $v.seguradora.nomeSeguradora.$invalid }"
              v-model="$v.seguradora.nomeSeguradora.$model"
              required
            />
            <div v-if="$v.seguradora.nomeSeguradora.$anyDirty && $v.seguradora.nomeSeguradora.$invalid">
              <small class="form-text text-danger" v-if="!$v.seguradora.nomeSeguradora.required"> This field is required. </small>
            </div>
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
            :disabled="$v.seguradora.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./seguradora-update.component.ts"></script>
