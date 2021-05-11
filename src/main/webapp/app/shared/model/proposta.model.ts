import { ICliente } from '@/shared/model/cliente.model';
import { ICorretor } from '@/shared/model/corretor.model';
import { IPlano } from '@/shared/model/plano.model';
import { ISeguradora } from '@/shared/model/seguradora.model';

import { Status } from '@/shared/model/enumerations/status.model';
export interface IProposta {
  id?: number;
  numeroProposta?: string;
  dataProposta?: Date;
  valorProposta?: number;
  status?: Status;
  cliente?: ICliente | null;
  corretor?: ICorretor | null;
  plano?: IPlano | null;
  seguradora?: ISeguradora | null;
}

export class Proposta implements IProposta {
  constructor(
    public id?: number,
    public numeroProposta?: string,
    public dataProposta?: Date,
    public valorProposta?: number,
    public status?: Status,
    public cliente?: ICliente | null,
    public corretor?: ICorretor | null,
    public plano?: IPlano | null,
    public seguradora?: ISeguradora | null
  ) {}
}
