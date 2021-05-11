<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="corretor">
        <h2 class="jh-entity-heading" data-cy="corretorDetailsHeading"><span>Corretor</span> {{ corretor.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome Corretor</span>
          </dt>
          <dd>
            <span>{{ corretor.nomeCorretor }}</span>
          </dd>
          <dt>
            <span>Usuario</span>
          </dt>
          <dd>
            <span>{{ corretor.usuario }}</span>
          </dd>
          <dt>
            <span>Data Nascimento</span>
          </dt>
          <dd>
            <span>{{ corretor.dataNascimento }}</span>
          </dd>
          <dt>
            <span>Foto</span>
          </dt>
          <dd>
            <div v-if="corretor.foto">
              <a v-on:click="openFile(corretor.fotoContentType, corretor.foto)">
                <img
                  v-bind:src="'data:' + corretor.fotoContentType + ';base64,' + corretor.foto"
                  style="max-width: 100%"
                  alt="corretor image"
                />
              </a>
              {{ corretor.fotoContentType }}, {{ byteSize(corretor.foto) }}
            </div>
          </dd>
          <dt>
            <span>Telefone</span>
          </dt>
          <dd>
            <span>{{ corretor.telefone }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="corretor.id" :to="{ name: 'CorretorEdit', params: { corretorId: corretor.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./corretor-details.component.ts"></script>
