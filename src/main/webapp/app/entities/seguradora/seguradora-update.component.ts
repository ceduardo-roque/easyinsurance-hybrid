import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import PropostaService from '@/entities/proposta/proposta.service';
import { IProposta } from '@/shared/model/proposta.model';

import { ISeguradora, Seguradora } from '@/shared/model/seguradora.model';
import SeguradoraService from './seguradora.service';

const validations: any = {
  seguradora: {
    nomeSeguradora: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class SeguradoraUpdate extends Vue {
  @Inject('seguradoraService') private seguradoraService: () => SeguradoraService;
  public seguradora: ISeguradora = new Seguradora();

  @Inject('propostaService') private propostaService: () => PropostaService;

  public propostas: IProposta[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.seguradoraId) {
        vm.retrieveSeguradora(to.params.seguradoraId);
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
    if (this.seguradora.id) {
      this.seguradoraService()
        .update(this.seguradora)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Seguradora is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.seguradoraService()
        .create(this.seguradora)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Seguradora is created with identifier ' + param.id;
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

  public retrieveSeguradora(seguradoraId): void {
    this.seguradoraService()
      .find(seguradoraId)
      .then(res => {
        this.seguradora = res;
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
