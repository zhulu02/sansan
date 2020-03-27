package com.charlie.entity;

import java.util.ArrayList;
import java.util.List;

public class IgUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IgUserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickName is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickName is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickName =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickName <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickName >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickName >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickName <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickName <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickName like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickName not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickName in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickName not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickName between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickName not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathIsNull() {
            addCriterion("headImgPath is null");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathIsNotNull() {
            addCriterion("headImgPath is not null");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathEqualTo(String value) {
            addCriterion("headImgPath =", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathNotEqualTo(String value) {
            addCriterion("headImgPath <>", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathGreaterThan(String value) {
            addCriterion("headImgPath >", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathGreaterThanOrEqualTo(String value) {
            addCriterion("headImgPath >=", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathLessThan(String value) {
            addCriterion("headImgPath <", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathLessThanOrEqualTo(String value) {
            addCriterion("headImgPath <=", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathLike(String value) {
            addCriterion("headImgPath like", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathNotLike(String value) {
            addCriterion("headImgPath not like", value, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathIn(List<String> values) {
            addCriterion("headImgPath in", values, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathNotIn(List<String> values) {
            addCriterion("headImgPath not in", values, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathBetween(String value1, String value2) {
            addCriterion("headImgPath between", value1, value2, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgpathNotBetween(String value1, String value2) {
            addCriterion("headImgPath not between", value1, value2, "headimgpath");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64IsNull() {
            addCriterion("headImgBase64 is null");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64IsNotNull() {
            addCriterion("headImgBase64 is not null");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64EqualTo(String value) {
            addCriterion("headImgBase64 =", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64NotEqualTo(String value) {
            addCriterion("headImgBase64 <>", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64GreaterThan(String value) {
            addCriterion("headImgBase64 >", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64GreaterThanOrEqualTo(String value) {
            addCriterion("headImgBase64 >=", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64LessThan(String value) {
            addCriterion("headImgBase64 <", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64LessThanOrEqualTo(String value) {
            addCriterion("headImgBase64 <=", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64Like(String value) {
            addCriterion("headImgBase64 like", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64NotLike(String value) {
            addCriterion("headImgBase64 not like", value, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64In(List<String> values) {
            addCriterion("headImgBase64 in", values, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64NotIn(List<String> values) {
            addCriterion("headImgBase64 not in", values, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64Between(String value1, String value2) {
            addCriterion("headImgBase64 between", value1, value2, "headimgbase64");
            return (Criteria) this;
        }

        public Criteria andHeadimgbase64NotBetween(String value1, String value2) {
            addCriterion("headImgBase64 not between", value1, value2, "headimgbase64");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}