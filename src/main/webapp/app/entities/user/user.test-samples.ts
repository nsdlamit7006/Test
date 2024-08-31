import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 17206,
  login: '3lw9j',
};

export const sampleWithPartialData: IUser = {
  id: 27705,
  login: 'vO@Vi',
};

export const sampleWithFullData: IUser = {
  id: 26228,
  login: 'C',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
