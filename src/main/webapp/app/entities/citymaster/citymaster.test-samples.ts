import { ICitymaster, NewCitymaster } from './citymaster.model';

export const sampleWithRequiredData: ICitymaster = {
  id: 12068,
};

export const sampleWithPartialData: ICitymaster = {
  id: 3780,
  cityCode: 'why meh',
  status: true,
};

export const sampleWithFullData: ICitymaster = {
  id: 16531,
  name: 'even out',
  cityCode: 'well right',
  status: false,
};

export const sampleWithNewData: NewCitymaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
