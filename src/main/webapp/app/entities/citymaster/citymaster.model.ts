export interface ICitymaster {
  id: number;
  name?: string | null;
  cityCode?: string | null;
  status?: boolean | null;
}

export type NewCitymaster = Omit<ICitymaster, 'id'> & { id: null };
