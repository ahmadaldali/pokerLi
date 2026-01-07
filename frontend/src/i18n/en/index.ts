import { localeRoutes } from "../routes";
import type { BaseTranslation } from "../i18n-types";

const PagesTranslation = {
  auth: {
    login: {
      title: "Sign in to your account",
      email: "Email address",
      password: "Password",
      submit: "Sign In",
      noAccount: "Don't have an account?",
      signUp: "Sign up",
    },
    signUp: {
      title: "Create your account",
      name: "Full Name",
      email: "Email address",
      password: "Password",
      confirmPassword: "Confirm Password",
      submit: "Sign Up",
      haveAccount: "Already have an account?",
      signIn: "Sign in",
    },
    logout: "logout",
  },
};

const ErrorsTranslation = {
  INVALID_CREDENTIALS: "Invalid email or password.",
  INTERNAL_SERVER_ERROR:
    "An internal server error occurred. Please try again later.",
};

const BlocksTranslation = {};

const en: BaseTranslation = {
  routes: localeRoutes("en"),
  pages: PagesTranslation,
  errors: ErrorsTranslation,
  blocks: BlocksTranslation,
};

export default en;
