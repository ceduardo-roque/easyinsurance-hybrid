/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SeguradoraDetailComponent from '@/entities/seguradora/seguradora-details.vue';
import SeguradoraClass from '@/entities/seguradora/seguradora-details.component';
import SeguradoraService from '@/entities/seguradora/seguradora.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Seguradora Management Detail Component', () => {
    let wrapper: Wrapper<SeguradoraClass>;
    let comp: SeguradoraClass;
    let seguradoraServiceStub: SinonStubbedInstance<SeguradoraService>;

    beforeEach(() => {
      seguradoraServiceStub = sinon.createStubInstance<SeguradoraService>(SeguradoraService);

      wrapper = shallowMount<SeguradoraClass>(SeguradoraDetailComponent, {
        store,
        localVue,
        router,
        provide: { seguradoraService: () => seguradoraServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSeguradora = { id: 123 };
        seguradoraServiceStub.find.resolves(foundSeguradora);

        // WHEN
        comp.retrieveSeguradora(123);
        await comp.$nextTick();

        // THEN
        expect(comp.seguradora).toBe(foundSeguradora);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSeguradora = { id: 123 };
        seguradoraServiceStub.find.resolves(foundSeguradora);

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
