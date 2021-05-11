import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Corretor = () => import('@/entities/corretor/corretor.vue');
// prettier-ignore
const CorretorUpdate = () => import('@/entities/corretor/corretor-update.vue');
// prettier-ignore
const CorretorDetails = () => import('@/entities/corretor/corretor-details.vue');
// prettier-ignore
const Cliente = () => import('@/entities/cliente/cliente.vue');
// prettier-ignore
const ClienteUpdate = () => import('@/entities/cliente/cliente-update.vue');
// prettier-ignore
const ClienteDetails = () => import('@/entities/cliente/cliente-details.vue');
// prettier-ignore
const Seguradora = () => import('@/entities/seguradora/seguradora.vue');
// prettier-ignore
const SeguradoraUpdate = () => import('@/entities/seguradora/seguradora-update.vue');
// prettier-ignore
const SeguradoraDetails = () => import('@/entities/seguradora/seguradora-details.vue');
// prettier-ignore
const Proposta = () => import('@/entities/proposta/proposta.vue');
// prettier-ignore
const PropostaUpdate = () => import('@/entities/proposta/proposta-update.vue');
// prettier-ignore
const PropostaDetails = () => import('@/entities/proposta/proposta-details.vue');
// prettier-ignore
const Plano = () => import('@/entities/plano/plano.vue');
// prettier-ignore
const PlanoUpdate = () => import('@/entities/plano/plano-update.vue');
// prettier-ignore
const PlanoDetails = () => import('@/entities/plano/plano-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/corretor',
    name: 'Corretor',
    component: Corretor,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/corretor/new',
    name: 'CorretorCreate',
    component: CorretorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/corretor/:corretorId/edit',
    name: 'CorretorEdit',
    component: CorretorUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/corretor/:corretorId/view',
    name: 'CorretorView',
    component: CorretorDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cliente',
    name: 'Cliente',
    component: Cliente,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cliente/new',
    name: 'ClienteCreate',
    component: ClienteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cliente/:clienteId/edit',
    name: 'ClienteEdit',
    component: ClienteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/cliente/:clienteId/view',
    name: 'ClienteView',
    component: ClienteDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/seguradora',
    name: 'Seguradora',
    component: Seguradora,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/seguradora/new',
    name: 'SeguradoraCreate',
    component: SeguradoraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/seguradora/:seguradoraId/edit',
    name: 'SeguradoraEdit',
    component: SeguradoraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/seguradora/:seguradoraId/view',
    name: 'SeguradoraView',
    component: SeguradoraDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proposta',
    name: 'Proposta',
    component: Proposta,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proposta/new',
    name: 'PropostaCreate',
    component: PropostaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proposta/:propostaId/edit',
    name: 'PropostaEdit',
    component: PropostaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proposta/:propostaId/view',
    name: 'PropostaView',
    component: PropostaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/plano',
    name: 'Plano',
    component: Plano,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/plano/new',
    name: 'PlanoCreate',
    component: PlanoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/plano/:planoId/edit',
    name: 'PlanoEdit',
    component: PlanoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/plano/:planoId/view',
    name: 'PlanoView',
    component: PlanoDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
