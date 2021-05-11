<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cliente">
        <h2 class="jh-entity-heading" data-cy="clienteDetailsHeading"><span>Cliente</span> {{ cliente.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome Cliente</span>
          </dt>
          <dd>
            <span>{{ cliente.nomeCliente }}</span>
          </dd>
          <dt>
            <span>Usuario</span>
          </dt>
          <dd>
            <span>{{ cliente.usuario }}</span>
          </dd>
          <dt>
            <span>Data Nascimento</span>
          </dt>
          <dd>
            <span>{{ cliente.dataNascimento }}</span>
          </dd>
          <dt>
            <span>Foto</span>
          </dt>
          <dd>
            <div v-if="cliente.foto">
              <a v-on:click="openFile(cliente.fotoContentType, cliente.foto)">
                <img
                  v-bind:src="'data:' + cliente.fotoContentType + ';base64,' + cliente.foto"
                  style="max-width: 100%"
                  alt="cliente image"
                />
              </a>
              {{ cliente.fotoContentType }}, {{ byteSize(cliente.foto) }}
            </div>
          </dd>
          <dt>
            <span>Telefone</span>
          </dt>
          <dd>
            <span>{{ cliente.telefone }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="cliente.id" :to="{ name: 'ClienteEdit', params: { clienteId: cliente.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cliente-details.component.ts"></script>
