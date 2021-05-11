/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PropostaUpdateComponent from '@/entities/proposta/proposta-update.vue';
import PropostaClass from '@/entities/proposta/proposta-update.component';
import PropostaService from '@/entities/proposta/proposta.service';

import ClienteService from '@/entities/cliente/cliente.service';

import CorretorService from '@/entities/corretor/corretor.service';

import PlanoService from '@/entities/plano/plano.service';

import SeguradoraService from '@/entities/seguradora/seguradora.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Proposta Management Update Component', () => {
    let wrapper: Wrapper<PropostaClass>;
    let comp: PropostaClass;
    let propostaServiceStub: SinonStubbedInstance<PropostaService>;

    beforeEach(() => {
      propostaServiceStub = sinon.createStubInstance<PropostaService>(PropostaService);

      wrapper = shallowMount<PropostaClass>(PropostaUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          propostaService: () => propostaServiceStub,

          clienteService: () => new ClienteService(),

          corretorService: () => new CorretorService(),

          planoService: () => new PlanoService(),

          seguradoraService: () => new SeguradoraService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proposta = entity;
        propostaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(propostaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proposta = entity;
        propostaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(propostaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProposta = { id: 123 };
        propostaServiceStub.find.resolves(foundProposta);
        propostaServiceStub.retrieve.resolves([foundProposta]);

        // WHEN
        comp.beforeRouteEnter({ params: { propostaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.proposta).toBe(foundProposta);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
