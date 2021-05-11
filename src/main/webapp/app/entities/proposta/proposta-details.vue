<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="proposta">
        <h2 class="jh-entity-heading" data-cy="propostaDetailsHeading"><span>Proposta</span> {{ proposta.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Numero Proposta</span>
          </dt>
          <dd>
            <span>{{ proposta.numeroProposta }}</span>
          </dd>
          <dt>
            <span>Data Proposta</span>
          </dt>
          <dd>
            <span>{{ proposta.dataProposta }}</span>
          </dd>
          <dt>
            <span>Valor Proposta</span>
          </dt>
          <dd>
            <span>{{ proposta.valorProposta }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ proposta.status }}</span>
          </dd>
          <dt>
            <span>Cliente</span>
          </dt>
          <dd>
            <div v-if="proposta.cliente">
              <router-link :to="{ name: 'ClienteView', params: { clienteId: proposta.cliente.id } }">{{
                proposta.cliente.nomeCliente
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Corretor</span>
          </dt>
          <dd>
            <div v-if="proposta.corretor">
              <router-link :to="{ name: 'CorretorView', params: { corretorId: proposta.corretor.id } }">{{
                proposta.corretor.nomeCorretor
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Plano</span>
          </dt>
          <dd>
            <div v-if="proposta.plano">
              <router-link :to="{ name: 'PlanoView', params: { planoId: proposta.plano.id } }">{{ proposta.plano.nomePlano }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Seguradora</span>
          </dt>
          <dd>
            <div v-if="proposta.seguradora">
              <router-link :to="{ name: 'SeguradoraView', params: { seguradoraId: proposta.seguradora.id } }">{{
                proposta.seguradora.nomeSeguradora
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="proposta.id" :to="{ name: 'PropostaEdit', params: { propostaId: proposta.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./proposta-details.component.ts"></script>
