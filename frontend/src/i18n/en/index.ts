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
  // API Errors
  INVALID_CREDENTIALS: "Invalid email or password",
  INTERNAL_SERVER_ERROR:
    "An internal server error occurred. Please try again later",
  
  // 
  required: '{field} is required',
  min_length: '{field} must be at least {min} characters long',
  max_length: '{field} must be at most {max} characters long',
  one_uppercase: '{field} must contain at least one uppercase letter',
  one_lowercase: '{field} must contain at least one lowercase letter',
  one_number: '{field} must contain at least one number',
  one_special: '{field} must contain at least one special character',
  password_mismatch: 'Passwords do not match',
  invalid_email: 'Invalid email address',
};

const BlocksTranslation = {
  header: {
    sprints: "Sprints",
  }
};

const FieldsTranslation = {
  name: {
    label: "Name",
    placeholder: "Ahmad Aldali",
  },
  email: {
    label: "Email",
    placeholder: "dev@ahmad.me"
  },
  password: {
    label: "Password",
    placeholder: "••••••••"
  },
  confirmPassword: {
    label: "Confirm Password",
    placeholder: "••••••••"
  }
};

const en: BaseTranslation = {
  routes: localeRoutes("en"),
  pages: PagesTranslation,
  errors: ErrorsTranslation,
  blocks: BlocksTranslation,
  fields: FieldsTranslation,
};

export default en;
