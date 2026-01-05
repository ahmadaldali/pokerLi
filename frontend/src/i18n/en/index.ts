import { localeRoutes } from '../routes';
import type { BaseTranslation } from '../i18n-types'


const en: BaseTranslation = {
   routes: localeRoutes('en'),
   hi: 'Hello',
   auth: {
      login: 'click here to login',
      logout: 'logout'
   },
   errors: {
      INVALID_CREDENTIALS: 'Invalid email or password.',
      INTERNAL_SERVER_ERROR: 'An internal server error occurred. Please try again later.'
   }
}

export default en;