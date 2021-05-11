/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CorretorUpdateComponent from '@/entities/corretor/corretor-update.vue';
import CorretorClass from '@/entities/corretor/corretor-update.component';
import CorretorService from '@/entities/corretor/corretor.service';

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
  describe('Corretor Management Update Component', () => {
    let wrapper: Wrapper<CorretorClass>;
    let comp: CorretorClass;
    let corretorServiceStub: SinonStubbedInstance<CorretorService>;

    beforeEach(() => {
      corretorServiceStub = sinon.createStubInstance<CorretorService>(CorretorService);

      wrapper = shallowMount<CorretorClass>(CorretorUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          corretorService: () => corretorServiceStub,

          propostaService: () => new PropostaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.corretor = entity;
        corretorServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(corretorServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.corretor = entity;
        corretorServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(corretorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCorretor = { id: 123 };
        corretorServiceStub.find.resolves(foundCorretor);
        corretorServiceStub.retrieve.resolves([foundCorretor]);

        // WHEN
        comp.beforeRouteEnter({ params: { corretorId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.corretor).toBe(foundCorretor);
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
