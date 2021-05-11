<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="easyinsuranceApp.proposta.home.createOrEditLabel" data-cy="PropostaCreateUpdateHeading">Create or edit a Proposta</h2>
        <div>
          <div class="form-group" v-if="proposta.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="proposta.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-numeroProposta">Numero Proposta</label>
            <input
              type="text"
              class="form-control"
              name="numeroProposta"
              id="proposta-numeroProposta"
              data-cy="numeroProposta"
              :class="{ valid: !$v.proposta.numeroProposta.$invalid, invalid: $v.proposta.numeroProposta.$invalid }"
              v-model="$v.proposta.numeroProposta.$model"
              required
            />
            <div v-if="$v.proposta.numeroProposta.$anyDirty && $v.proposta.numeroProposta.$invalid">
              <small class="form-text text-danger" v-if="!$v.proposta.numeroProposta.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-dataProposta">Data Proposta</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="proposta-dataProposta"
                  v-model="$v.proposta.dataProposta.$model"
                  name="dataProposta"
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
                id="proposta-dataProposta"
                data-cy="dataProposta"
                type="text"
                class="form-control"
                name="dataProposta"
                :class="{ valid: !$v.proposta.dataProposta.$invalid, invalid: $v.proposta.dataProposta.$invalid }"
                v-model="$v.proposta.dataProposta.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.proposta.dataProposta.$anyDirty && $v.proposta.dataProposta.$invalid">
              <small class="form-text text-danger" v-if="!$v.proposta.dataProposta.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-valorProposta">Valor Proposta</label>
            <input
              type="number"
              class="form-control"
              name="valorProposta"
              id="proposta-valorProposta"
              data-cy="valorProposta"
              :class="{ valid: !$v.proposta.valorProposta.$invalid, invalid: $v.proposta.valorProposta.$invalid }"
              v-model.number="$v.proposta.valorProposta.$model"
              required
            />
            <div v-if="$v.proposta.valorProposta.$anyDirty && $v.proposta.valorProposta.$invalid">
              <small class="form-text text-danger" v-if="!$v.proposta.valorProposta.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.proposta.valorProposta.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.proposta.status.$invalid, invalid: $v.proposta.status.$invalid }"
              v-model="$v.proposta.status.$model"
              id="proposta-status"
              data-cy="status"
              required
            >
              <option value="ATIVO">ATIVO</option>
              <option value="CANCELADO">CANCELADO</option>
              <option value="PENDENTE">PENDENTE</option>
            </select>
            <div v-if="$v.proposta.status.$anyDirty && $v.proposta.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.proposta.status.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-cliente">Cliente</label>
            <select class="form-control" id="proposta-cliente" data-cy="cliente" name="cliente" v-model="proposta.cliente">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="proposta.cliente && clienteOption.id === proposta.cliente.id ? proposta.cliente : clienteOption"
                v-for="clienteOption in clientes"
                :key="clienteOption.id"
              >
                {{ clienteOption.nomeCliente }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-corretor">Corretor</label>
            <select class="form-control" id="proposta-corretor" data-cy="corretor" name="corretor" v-model="proposta.corretor">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="proposta.corretor && corretorOption.id === proposta.corretor.id ? proposta.corretor : corretorOption"
                v-for="corretorOption in corretors"
                :key="corretorOption.id"
              >
                {{ corretorOption.nomeCorretor }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-plano">Plano</label>
            <select class="form-control" id="proposta-plano" data-cy="plano" name="plano" v-model="proposta.plano">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="proposta.plano && planoOption.id === proposta.plano.id ? proposta.plano : planoOption"
                v-for="planoOption in planos"
                :key="planoOption.id"
              >
                {{ planoOption.nomePlano }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="proposta-seguradora">Seguradora</label>
            <select class="form-control" id="proposta-seguradora" data-cy="seguradora" name="seguradora" v-model="proposta.seguradora">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  proposta.seguradora && seguradoraOption.id === proposta.seguradora.id ? proposta.seguradora : seguradoraOption
                "
                v-for="seguradoraOption in seguradoras"
                :key="seguradoraOption.id"
              >
                {{ seguradoraOption.nomeSeguradora }}
              </option>
            </select>
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
            :disabled="$v.proposta.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./proposta-update.component.ts"></script>
