import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import PropostaService from '@/entities/proposta/proposta.service';
import { IProposta } from '@/shared/model/proposta.model';

import { IPlano, Plano } from '@/shared/model/plano.model';
import PlanoService from './plano.service';

const validations: any = {
  plano: {
    nomePlano: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PlanoUpdate extends Vue {
  @Inject('planoService') private planoService: () => PlanoService;
  public plano: IPlano = new Plano();

  @Inject('propostaService') private propostaService: () => PropostaService;

  public propostas: IProposta[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.planoId) {
        vm.retrievePlano(to.params.planoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.plano.id) {
      this.planoService()
        .update(this.plano)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Plano is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.planoService()
        .create(this.plano)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Plano is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrievePlano(planoId): void {
    this.planoService()
      .find(planoId)
      .then(res => {
        this.plano = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.propostaService()
      .retrieve()
      .then(res => {
        this.propostas = res.data;
      });
  }
}
