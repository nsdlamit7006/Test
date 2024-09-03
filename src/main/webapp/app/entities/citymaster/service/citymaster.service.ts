import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICitymaster, NewCitymaster } from '../citymaster.model';

export type PartialUpdateCitymaster = Partial<ICitymaster> & Pick<ICitymaster, 'id'>;

export type EntityResponseType = HttpResponse<ICitymaster>;
export type EntityArrayResponseType = HttpResponse<ICitymaster[]>;

@Injectable({ providedIn: 'root' })
export class CitymasterService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/citymasters');

  create(citymaster: NewCitymaster): Observable<EntityResponseType> {
    return this.http.post<ICitymaster>(this.resourceUrl, citymaster, { observe: 'response' });
  }

  update(citymaster: ICitymaster): Observable<EntityResponseType> {
    return this.http.put<ICitymaster>(`${this.resourceUrl}/${this.getCitymasterIdentifier(citymaster)}`, citymaster, {
      observe: 'response',
    });
  }

  partialUpdate(citymaster: PartialUpdateCitymaster): Observable<EntityResponseType> {
    return this.http.patch<ICitymaster>(`${this.resourceUrl}/${this.getCitymasterIdentifier(citymaster)}`, citymaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICitymaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICitymaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCitymasterIdentifier(citymaster: Pick<ICitymaster, 'id'>): number {
    return citymaster.id;
  }

  compareCitymaster(o1: Pick<ICitymaster, 'id'> | null, o2: Pick<ICitymaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCitymasterIdentifier(o1) === this.getCitymasterIdentifier(o2) : o1 === o2;
  }

  addCitymasterToCollectionIfMissing<Type extends Pick<ICitymaster, 'id'>>(
    citymasterCollection: Type[],
    ...citymastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const citymasters: Type[] = citymastersToCheck.filter(isPresent);
    if (citymasters.length > 0) {
      const citymasterCollectionIdentifiers = citymasterCollection.map(citymasterItem => this.getCitymasterIdentifier(citymasterItem));
      const citymastersToAdd = citymasters.filter(citymasterItem => {
        const citymasterIdentifier = this.getCitymasterIdentifier(citymasterItem);
        if (citymasterCollectionIdentifiers.includes(citymasterIdentifier)) {
          return false;
        }
        citymasterCollectionIdentifiers.push(citymasterIdentifier);
        return true;
      });
      return [...citymastersToAdd, ...citymasterCollection];
    }
    return citymasterCollection;
  }
}
