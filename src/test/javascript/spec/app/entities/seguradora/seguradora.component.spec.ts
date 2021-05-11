/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SeguradoraComponent from '@/entities/seguradora/seguradora.vue';
import SeguradoraClass from '@/entities/seguradora/seguradora.component';
import SeguradoraService from '@/entities/seguradora/seguradora.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Seguradora Management Component', () => {
    let wrapper: Wrapper<SeguradoraClass>;
    let comp: SeguradoraClass;
    let seguradoraServiceStub: SinonStubbedInstance<SeguradoraService>;

    beforeEach(() => {
      seguradoraServiceStub = sinon.createStubInstance<SeguradoraService>(SeguradoraService);
      seguradoraServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SeguradoraClass>(SeguradoraComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          seguradoraService: () => seguradoraServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      seguradoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSeguradoras();
      await comp.$nextTick();

      // THEN
      expect(seguradoraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.seguradoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      seguradoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(seguradoraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.seguradoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      seguradoraServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(seguradoraServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      seguradoraServiceStub.retrieve.reset();
      seguradoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(seguradoraServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.seguradoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      seguradoraServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSeguradora();
      await comp.$nextTick();

      // THEN
      expect(seguradoraServiceStub.delete.called).toBeTruthy();
      expect(seguradoraServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
