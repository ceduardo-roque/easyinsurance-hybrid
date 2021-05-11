import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISeguradora } from '@/shared/model/seguradora.model';
import SeguradoraService from './seguradora.service';

@Component
export default class SeguradoraDetails extends Vue {
  @Inject('seguradoraService') private seguradoraService: () => SeguradoraService;
  public seguradora: ISeguradora = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.seguradoraId) {
        vm.retrieveSeguradora(to.params.seguradoraId);
      }
    });
  }

  public retrieveSeguradora(seguradoraId) {
    this.seguradoraService()
      .find(seguradoraId)
      .then(res => {
        this.seguradora = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
