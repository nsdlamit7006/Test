import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '39bc7d8d-1002-436d-ac26-6b9992050ff0',
};

export const sampleWithPartialData: IAuthority = {
  name: '4500bd5d-7c37-4954-82ce-4dc08f870634',
};

export const sampleWithFullData: IAuthority = {
  name: '486b1a6c-a8e5-4edf-84f2-7c09c9b8568e',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
