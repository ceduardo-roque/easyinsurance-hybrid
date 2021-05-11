<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="seguradora">
        <h2 class="jh-entity-heading" data-cy="seguradoraDetailsHeading"><span>Seguradora</span> {{ seguradora.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome Seguradora</span>
          </dt>
          <dd>
            <span>{{ seguradora.nomeSeguradora }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="seguradora.id"
          :to="{ name: 'SeguradoraEdit', params: { seguradoraId: seguradora.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./seguradora-details.component.ts"></script>
