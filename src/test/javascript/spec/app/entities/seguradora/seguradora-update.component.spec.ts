/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import SeguradoraUpdateComponent from '@/entities/seguradora/seguradora-update.vue';
import SeguradoraClass from '@/entities/seguradora/seguradora-update.component';
import SeguradoraService from '@/entities/seguradora/seguradora.service';

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
  describe('Seguradora Management Update Component', () => {
    let wrapper: Wrapper<SeguradoraClass>;
    let comp: SeguradoraClass;
    let seguradoraServiceStub: SinonStubbedInstance<SeguradoraService>;

    beforeEach(() => {
      seguradoraServiceStub = sinon.createStubInstance<SeguradoraService>(SeguradoraService);

      wrapper = shallowMount<SeguradoraClass>(SeguradoraUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          seguradoraService: () => seguradoraServiceStub,

          propostaService: () => new PropostaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.seguradora = entity;
        seguradoraServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(seguradoraServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.seguradora = entity;
        seguradoraServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(seguradoraServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSeguradora = { id: 123 };
        seguradoraServiceStub.find.resolves(foundSeguradora);
        seguradoraServiceStub.retrieve.resolves([foundSeguradora]);

        // WHEN
        comp.beforeRouteEnter({ params: { seguradoraId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.seguradora).toBe(foundSeguradora);
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
