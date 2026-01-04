import { localeRoutes } from '../routes';
import type { BaseTranslation } from '../i18n-types'


const en: BaseTranslation = {
   routes: localeRoutes('en'),
   hi: 'Hello',
   auth: {
      login: 'click here to login',
      logout: 'logout'
   }
}

export default en;