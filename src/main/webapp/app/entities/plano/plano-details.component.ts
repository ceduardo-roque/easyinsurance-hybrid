import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlano } from '@/shared/model/plano.model';
import PlanoService from './plano.service';

@Component
export default class PlanoDetails extends Vue {
  @Inject('planoService') private planoService: () => PlanoService;
  public plano: IPlano = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.planoId) {
        vm.retrievePlano(to.params.planoId);
      }
    });
  }

  public retrievePlano(planoId) {
    this.planoService()
      .find(planoId)
      .then(res => {
        this.plano = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
