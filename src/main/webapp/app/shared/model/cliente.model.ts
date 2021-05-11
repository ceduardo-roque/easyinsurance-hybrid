import { IProposta } from '@/shared/model/proposta.model';

export interface ICliente {
  id?: number;
  nomeCliente?: string;
  usuario?: string;
  dataNascimento?: Date | null;
  fotoContentType?: string | null;
  foto?: string | null;
  telefone?: string | null;
  propostas?: IProposta[] | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nomeCliente?: string,
    public usuario?: string,
    public dataNascimento?: Date | null,
    public fotoContentType?: string | null,
    public foto?: string | null,
    public telefone?: string | null,
    public propostas?: IProposta[] | null
  ) {}
}
