import { localeRoutes } from '../routes';
import type { BaseTranslation } from '../i18n-types'

const ro: BaseTranslation = {
   routes: localeRoutes('ro'),
   hi: 'Hello',
   auth: {
      login: 'EN Sclick here to login',
      logout: 'logout'
   }
}

export default ro;