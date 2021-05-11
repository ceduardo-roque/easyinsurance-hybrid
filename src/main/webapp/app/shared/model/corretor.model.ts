import { IProposta } from '@/shared/model/proposta.model';

export interface ICorretor {
  id?: number;
  nomeCorretor?: string;
  usuario?: string;
  dataNascimento?: Date | null;
  fotoContentType?: string | null;
  foto?: string | null;
  telefone?: string | null;
  corretors?: IProposta[] | null;
}

export class Corretor implements ICorretor {
  constructor(
    public id?: number,
    public nomeCorretor?: string,
    public usuario?: string,
    public dataNascimento?: Date | null,
    public fotoContentType?: string | null,
    public foto?: string | null,
    public telefone?: string | null,
    public corretors?: IProposta[] | null
  ) {}
}
