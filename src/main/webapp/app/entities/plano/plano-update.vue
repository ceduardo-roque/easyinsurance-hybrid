<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="easyinsuranceApp.plano.home.createOrEditLabel" data-cy="PlanoCreateUpdateHeading">Create or edit a Plano</h2>
        <div>
          <div class="form-group" v-if="plano.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="plano.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="plano-nomePlano">Nome Plano</label>
            <input
              type="text"
              class="form-control"
              name="nomePlano"
              id="plano-nomePlano"
              data-cy="nomePlano"
              :class="{ valid: !$v.plano.nomePlano.$invalid, invalid: $v.plano.nomePlano.$invalid }"
              v-model="$v.plano.nomePlano.$model"
              required
            />
            <div v-if="$v.plano.nomePlano.$anyDirty && $v.plano.nomePlano.$invalid">
              <small class="form-text text-danger" v-if="!$v.plano.nomePlano.required"> This field is required. </small>
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
            :disabled="$v.plano.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./plano-update.component.ts"></script>
