/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PlanoDetailComponent from '@/entities/plano/plano-details.vue';
import PlanoClass from '@/entities/plano/plano-details.component';
import PlanoService from '@/entities/plano/plano.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Plano Management Detail Component', () => {
    let wrapper: Wrapper<PlanoClass>;
    let comp: PlanoClass;
    let planoServiceStub: SinonStubbedInstance<PlanoService>;

    beforeEach(() => {
      planoServiceStub = sinon.createStubInstance<PlanoService>(PlanoService);

      wrapper = shallowMount<PlanoClass>(PlanoDetailComponent, {
        store,
        localVue,
        router,
        provide: { planoService: () => planoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlano = { id: 123 };
        planoServiceStub.find.resolves(foundPlano);

        // WHEN
        comp.retrievePlano(123);
        await comp.$nextTick();

        // THEN
        expect(comp.plano).toBe(foundPlano);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlano = { id: 123 };
        planoServiceStub.find.resolves(foundPlano);

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
