<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="plano">
        <h2 class="jh-entity-heading" data-cy="planoDetailsHeading"><span>Plano</span> {{ plano.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome Plano</span>
          </dt>
          <dd>
            <span>{{ plano.nomePlano }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="plano.id" :to="{ name: 'PlanoEdit', params: { planoId: plano.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./plano-details.component.ts"></script>
