import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal } from 'vuelidate/lib/validators';

import ClienteService from '@/entities/cliente/cliente.service';
import { ICliente } from '@/shared/model/cliente.model';

import CorretorService from '@/entities/corretor/corretor.service';
import { ICorretor } from '@/shared/model/corretor.model';

import PlanoService from '@/entities/plano/plano.service';
import { IPlano } from '@/shared/model/plano.model';

import SeguradoraService from '@/entities/seguradora/seguradora.service';
import { ISeguradora } from '@/shared/model/seguradora.model';

import { IProposta, Proposta } from '@/shared/model/proposta.model';
import PropostaService from './proposta.service';

const validations: any = {
  proposta: {
    numeroProposta: {
      required,
    },
    dataProposta: {
      required,
    },
    valorProposta: {
      required,
      decimal,
    },
    status: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PropostaUpdate extends Vue {
  @Inject('propostaService') private propostaService: () => PropostaService;
  public proposta: IProposta = new Proposta();

  @Inject('clienteService') private clienteService: () => ClienteService;

  public clientes: ICliente[] = [];

  @Inject('corretorService') private corretorService: () => CorretorService;

  public corretors: ICorretor[] = [];

  @Inject('planoService') private planoService: () => PlanoService;

  public planos: IPlano[] = [];

  @Inject('seguradoraService') private seguradoraService: () => SeguradoraService;

  public seguradoras: ISeguradora[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.propostaId) {
        vm.retrieveProposta(to.params.propostaId);
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
    if (this.proposta.id) {
      this.propostaService()
        .update(this.proposta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Proposta is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.propostaService()
        .create(this.proposta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Proposta is created with identifier ' + param.id;
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

  public retrieveProposta(propostaId): void {
    this.propostaService()
      .find(propostaId)
      .then(res => {
        this.proposta = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clienteService()
      .retrieve()
      .then(res => {
        this.clientes = res.data;
      });
    this.corretorService()
      .retrieve()
      .then(res => {
        this.corretors = res.data;
      });
    this.planoService()
      .retrieve()
      .then(res => {
        this.planos = res.data;
      });
    this.seguradoraService()
      .retrieve()
      .then(res => {
        this.seguradoras = res.data;
      });
  }
}
