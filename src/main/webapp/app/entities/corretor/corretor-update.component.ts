import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';

import PropostaService from '@/entities/proposta/proposta.service';
import { IProposta } from '@/shared/model/proposta.model';

import { ICorretor, Corretor } from '@/shared/model/corretor.model';
import CorretorService from './corretor.service';

const validations: any = {
  corretor: {
    nomeCorretor: {
      required,
    },
    usuario: {
      required,
    },
    dataNascimento: {},
    foto: {},
    telefone: {},
  },
};

@Component({
  validations,
})
export default class CorretorUpdate extends mixins(JhiDataUtils) {
  @Inject('corretorService') private corretorService: () => CorretorService;
  public corretor: ICorretor = new Corretor();

  @Inject('propostaService') private propostaService: () => PropostaService;

  public propostas: IProposta[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.corretorId) {
        vm.retrieveCorretor(to.params.corretorId);
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
    if (this.corretor.id) {
      this.corretorService()
        .update(this.corretor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Corretor is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.corretorService()
        .create(this.corretor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Corretor is created with identifier ' + param.id;
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

  public retrieveCorretor(corretorId): void {
    this.corretorService()
      .find(corretorId)
      .then(res => {
        this.corretor = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.corretor && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.corretor, field)) {
        this.corretor[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.corretor, fieldContentType)) {
        this.corretor[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.propostaService()
      .retrieve()
      .then(res => {
        this.propostas = res.data;
      });
  }
}
