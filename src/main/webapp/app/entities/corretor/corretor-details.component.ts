import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ICorretor } from '@/shared/model/corretor.model';
import CorretorService from './corretor.service';

@Component
export default class CorretorDetails extends mixins(JhiDataUtils) {
  @Inject('corretorService') private corretorService: () => CorretorService;
  public corretor: ICorretor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.corretorId) {
        vm.retrieveCorretor(to.params.corretorId);
      }
    });
  }

  public retrieveCorretor(corretorId) {
    this.corretorService()
      .find(corretorId)
      .then(res => {
        this.corretor = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
