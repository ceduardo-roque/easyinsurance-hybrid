import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProposta } from '@/shared/model/proposta.model';
import PropostaService from './proposta.service';

@Component
export default class PropostaDetails extends Vue {
  @Inject('propostaService') private propostaService: () => PropostaService;
  public proposta: IProposta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.propostaId) {
        vm.retrieveProposta(to.params.propostaId);
      }
    });
  }

  public retrieveProposta(propostaId) {
    this.propostaService()
      .find(propostaId)
      .then(res => {
        this.proposta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
