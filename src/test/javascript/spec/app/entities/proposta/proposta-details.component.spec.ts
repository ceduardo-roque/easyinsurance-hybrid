/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PropostaDetailComponent from '@/entities/proposta/proposta-details.vue';
import PropostaClass from '@/entities/proposta/proposta-details.component';
import PropostaService from '@/entities/proposta/proposta.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Proposta Management Detail Component', () => {
    let wrapper: Wrapper<PropostaClass>;
    let comp: PropostaClass;
    let propostaServiceStub: SinonStubbedInstance<PropostaService>;

    beforeEach(() => {
      propostaServiceStub = sinon.createStubInstance<PropostaService>(PropostaService);

      wrapper = shallowMount<PropostaClass>(PropostaDetailComponent, {
        store,
        localVue,
        router,
        provide: { propostaService: () => propostaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProposta = { id: 123 };
        propostaServiceStub.find.resolves(foundProposta);

        // WHEN
        comp.retrieveProposta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.proposta).toBe(foundProposta);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProposta = { id: 123 };
        propostaServiceStub.find.resolves(foundProposta);

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
