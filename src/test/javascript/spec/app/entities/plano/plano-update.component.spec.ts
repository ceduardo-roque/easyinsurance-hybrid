/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PlanoUpdateComponent from '@/entities/plano/plano-update.vue';
import PlanoClass from '@/entities/plano/plano-update.component';
import PlanoService from '@/entities/plano/plano.service';

import PropostaService from '@/entities/proposta/proposta.service';

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
  describe('Plano Management Update Component', () => {
    let wrapper: Wrapper<PlanoClass>;
    let comp: PlanoClass;
    let planoServiceStub: SinonStubbedInstance<PlanoService>;

    beforeEach(() => {
      planoServiceStub = sinon.createStubInstance<PlanoService>(PlanoService);

      wrapper = shallowMount<PlanoClass>(PlanoUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          planoService: () => planoServiceStub,

          propostaService: () => new PropostaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.plano = entity;
        planoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(planoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.plano = entity;
        planoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(planoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlano = { id: 123 };
        planoServiceStub.find.resolves(foundPlano);
        planoServiceStub.retrieve.resolves([foundPlano]);

        // WHEN
        comp.beforeRouteEnter({ params: { planoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.plano).toBe(foundPlano);
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
