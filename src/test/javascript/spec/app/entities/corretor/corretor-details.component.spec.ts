/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CorretorDetailComponent from '@/entities/corretor/corretor-details.vue';
import CorretorClass from '@/entities/corretor/corretor-details.component';
import CorretorService from '@/entities/corretor/corretor.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Corretor Management Detail Component', () => {
    let wrapper: Wrapper<CorretorClass>;
    let comp: CorretorClass;
    let corretorServiceStub: SinonStubbedInstance<CorretorService>;

    beforeEach(() => {
      corretorServiceStub = sinon.createStubInstance<CorretorService>(CorretorService);

      wrapper = shallowMount<CorretorClass>(CorretorDetailComponent, {
        store,
        localVue,
        router,
        provide: { corretorService: () => corretorServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCorretor = { id: 123 };
        corretorServiceStub.find.resolves(foundCorretor);

        // WHEN
        comp.retrieveCorretor(123);
        await comp.$nextTick();

        // THEN
        expect(comp.corretor).toBe(foundCorretor);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCorretor = { id: 123 };
        corretorServiceStub.find.resolves(foundCorretor);

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
